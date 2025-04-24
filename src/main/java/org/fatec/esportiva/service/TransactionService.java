package org.fatec.esportiva.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.fatec.esportiva.entity.*;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.dto.response.TransactionResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ClientService clientService;
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final ExchangeVoucherService exchangeVoucherService;

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
    public Transaction generateTransaction(CheckoutSession checkoutSession) {
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

        return transactionRepository.save(transaction);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve){
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
                refundSingleVoucher(transaction);
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
                refundSingleVoucher(transaction);
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

    private void propagateStatusToOrder(Transaction transaction, boolean approve) {

        // Propaga o status para os pedidos
        // Se os pedidos retornarem algum valor, quer dizer que é para gerar cupons de
        // reembolso
        if(transaction.getStatus() == OrderStatus.EM_TROCA && approve){
            for (Order order : transaction.getOrders()) {
                orderService.tradeOrder(order.getId(), (short) order.getQuantity());
            }
            return;
        }
        for (Order order : transaction.getOrders()) {
            orderService.changeState(order.getId(), approve, false);
        }
    }

    // Gera cupom para o cliente, reembolsando o produto
    private void refundSingleVoucher(Transaction transaction) {
        Client client = transaction.getClient();
        BigDecimal voucherValue = transaction.getTotalCost();
        if (voucherValue.compareTo(BigDecimal.ZERO) < 1)
            return;
        // Cria um novo cupom
        exchangeVoucherService.createExchangeVoucher(client, voucherValue);
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

    public void requestTrade(Long id) {
        Client client = clientService.getAuthenticatedClient();
        Transaction transaction = transactionRepository.findByClientAndIdAndStatus(client, id, OrderStatus.ENTREGUE   ).orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        changeState(transaction.getId(), true);
    }
}