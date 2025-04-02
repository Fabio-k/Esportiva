package org.fatec.esportiva.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.request.TransactionDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final OrderService orderService;

    // Constante para melhorar legibilidade
    private static final BigDecimal ZERO = BigDecimal.valueOf(0);

    public List<TransactionDto> getTransactions(OrderStatus status) {
        return transactionRepository.findAllByStatus(status)
                .stream().map(TransactionMapper::toTransactionDto).toList();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) throws Exception {
        Transaction transaction = getNonOptional(transactionRepository.findById(id));
        OrderStatus status = transaction.getStatus();

        // Máquina de estados
        if (status == OrderStatus.CARRINHO_COMPRAS) {
            if (approve == true) {
                // Cartão foi validado antes se chegou aqui
                transaction.setStatus(OrderStatus.EM_PROCESSAMENTO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Não faz nada, porque nada foi aprovado ainda
            }
        }

        else if (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve == true) {
                // Dá a baixa no estoque aqui e desbloqueia os produtos
                transaction.setStatus(OrderStatus.EM_TRANSITO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
                propagateStatusToOrder(transaction, approve);
            }
        }

        else if (status == OrderStatus.EM_TRANSITO) {
            if (approve == true) {
                // Vai para a casa do cliente
                transaction.setStatus(OrderStatus.ENTREGUE);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Produto que estava indo para o cliente, volta para o estoque antes de chegar
                // na sua casa
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
                propagateStatusToOrder(transaction, approve);
            }
        }

        else if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                // Aparece um aviso para o cliente que a troca foi aceita
                // Pode ser uma lista de produtos que estão nesse estado
                transaction.setStatus(OrderStatus.EM_TROCA);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Não faz nada, porque o cliente só pode solicitar devolução ou fazer nada
            }
        }

        else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                // Troca aceita
                transaction.setStatus(OrderStatus.TROCADO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Troca recusada, não faz nada
                transaction.setStatus(OrderStatus.TROCA_RECUSADA);
                propagateStatusToOrder(transaction, approve);
            }

        }

        else if (status == OrderStatus.TROCADO) {
            if (approve == true) {
                // Repõe o estoque quando a troca é finalizada
                // Reembolsa o cliente
                transaction.setStatus(OrderStatus.TROCA_FINALIZADA);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Produto não chegou na loja, troca recusada novamente
                transaction.setStatus(OrderStatus.TROCA_RECUSADA);
                propagateStatusToOrder(transaction, approve);
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
        if (totalValue.compareTo(ZERO) == 1) {
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
}