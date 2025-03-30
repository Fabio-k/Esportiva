package org.fatec.esportiva.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.request.TransactionDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;

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
                transaction.setStatus(OrderStatus.EM_PROCESSAMENTO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        }

        // O cartão de crédito não aprova, ele só reprova se for inválido o cartão
        else if (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.EM_TRANSITO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        }

        else if (status == OrderStatus.EM_TRANSITO) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.ENTREGUE);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        }

        else if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.EM_TROCA);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        }

        else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.TROCADO);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }

        }

        else if (status == OrderStatus.TROCADO) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.TROCA_FINALIZADA);
                propagateStatusToOrder(transaction, approve);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }

        }

        else {
            // Não faz nada nos outros estados
        }

        transactionRepository.save(transaction);
    }

    private void propagateStatusToOrder(Transaction transaction, boolean approve) throws Exception {
        for (Order order : transaction.getOrders()) {
            orderService.changeState(order.getId(), approve);
        }
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