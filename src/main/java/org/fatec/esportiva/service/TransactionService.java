package org.fatec.esportiva.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.fatec.esportiva.entity.*;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.dto.response.TransactionResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;

    // Constante para melhorar legibilidade
    private static final BigDecimal ZERO = BigDecimal.valueOf(0);

    public List<TransactionResponseDto> getTransactions() {
        return transactionRepository.findAllByClientOrderByPurchaseDateDesc(clientService.getAuthenticatedClient())
                .stream().map(TransactionMapper::toTransactionResponseDto).toList();
    }

    public List<TransactionDto> getTransactions(OrderStatus status) {
        return transactionRepository.findAllByStatus(status)
                .stream().map(TransactionMapper::toTransactionDto).toList();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public Transaction generateTransaction(CheckoutSession checkoutSession){
        Address address = addressService.findById(checkoutSession.getAddress().getId());
        List<CreditCard> creditCards = checkoutSession.getCreditCardIds().stream().map(creditCardService::findCreditCard).toList();

        Client client = clientService.getAuthenticatedClient();
        Transaction transaction = Transaction.builder()
                .client(client)
                .status(OrderStatus.EM_PROCESSAMENTO)
                .purchaseDate(LocalDate.now())
                .build();


        List<Order> orders = client.getCart().getCartItems().stream()
                .map(cartItem -> {
                    Order order = CartItemMapper.toOrder(cartItem);
                    order.setTransaction(transaction);
                    order.setStatus(OrderStatus.EM_PROCESSAMENTO);
                    return order;
                }).toList();

        transaction.setOrders(orders);

        cartService.cleanCart();
        return transactionRepository.save(transaction);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) throws Exception {
        Transaction transaction = getNonOptional(transactionRepository.findById(id));
        OrderStatus status = transaction.getStatus();

        if (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve) {
                transaction.setStatus(OrderStatus.EM_TRANSITO);
                propagateStatusToOrder(transaction, true);
            } else {
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
                propagateStatusToOrder(transaction, false);
            }
        } else if (status == OrderStatus.EM_TRANSITO) {
            if (approve) {
                // Vai para a casa do cliente
                transaction.setStatus(OrderStatus.ENTREGUE);
                propagateStatusToOrder(transaction, true);
            } else {
                // Produto que estava indo para o cliente, volta para o estoque antes de chegar
                // na sua casa
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
                propagateStatusToOrder(transaction, false);
            }
        }

        else if (status == OrderStatus.ENTREGUE) {
            if (approve) {
                // Aparece um aviso para o cliente que a troca foi aceita
                // Pode ser uma lista de produtos que estão nesse estado
                transaction.setStatus(OrderStatus.EM_TROCA);
                propagateStatusToOrder(transaction, true);
            } else {
                // Não faz nada, porque o cliente só pode solicitar devolução ou fazer nada
            }
        }

        else if (status == OrderStatus.EM_TROCA) {
            if (approve) {
                // Troca aceita
                transaction.setStatus(OrderStatus.TROCADO);
                propagateStatusToOrder(transaction, true);
            } else {
                // Troca recusada, não faz nada
                transaction.setStatus(OrderStatus.TROCA_RECUSADA);
                propagateStatusToOrder(transaction, approve);
            }

        }

        else if (status == OrderStatus.TROCADO) {
            if (approve) {
                // Repõe o estoque quando a troca é finalizada
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.TROCA_FINALIZADA);
                propagateStatusToOrder(transaction, true);
            } else {
                // Produto não chegou na loja, troca recusada novamente
                transaction.setStatus(OrderStatus.TROCA_RECUSADA);
                propagateStatusToOrder(transaction, false);
            }

        }

        else {
            // Não faz nada nos outros estados
        }

        // Atualiza a transação
        // Se aprovar todos os pedidos da transação, pode apagar ele
        if (transaction.getOrders().isEmpty()) {
            transactionRepository.delete(transaction);
        }
        // Do contrário, atualiza a transação
        else {
            transactionRepository.save(transaction);
        }
    }

    private void propagateStatusToOrder(Transaction transaction, boolean approve) throws Exception {
        BigDecimal totalValue = ZERO;

        // Propaga o status para os pedidos
        // Se os pedidos retornarem algum valor, quer dizer que é para gerar cupons de
        // reembolso
        for (Order order : transaction.getOrders()) {
            BigDecimal value = orderService.changeState(order.getId(), approve);
            totalValue = totalValue.add(value);
        }

        // Se o valor do cupom for maior que zero, quer dizer que há algo para
        // reembolsar
        if (totalValue.compareTo(ZERO) > 0) {
            refundSingleVoucher(transaction, totalValue);
        }
    }

    // Gera cupom para o cliente, reembolsando o produto
    private void refundSingleVoucher(Transaction transaction, BigDecimal voucherValue) {
        Client client = transaction.getClient();

        // Cria um novo cupom
        ExchangeVoucher exchangeVoucher = new ExchangeVoucher();
        exchangeVoucher.setId(null);
        exchangeVoucher.setValue(voucherValue);
        exchangeVoucher.setClient(client);

        // Adiciona o novo cupom a lista de cupons que o cliente já tinha
        List<ExchangeVoucher> exchangeVouchers = client.getExchangeVouchers();
        exchangeVouchers.add(exchangeVoucher);
        client.setExchangeVouchers(exchangeVouchers);
        clientRepository.save(client);
    }

    // Remove o "Optional" do tipo que o linter reclama
    private static <T> T getNonOptional(Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("Optional está vazio.");
        }
    }

    @Transactional
    public void denyTransaction(Transaction transaction) {
        List<Order> orders = transaction.getOrders();

        orders.forEach(order -> {
            productService.returnBlockedProductQuantity(order.getProduct().getId(), (short) order.getQuantity());
        });

        transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
    }
}