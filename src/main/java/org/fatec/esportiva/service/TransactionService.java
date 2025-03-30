package org.fatec.esportiva.service;

import java.util.List;
import java.util.Optional;

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

    public List<TransactionDto> getTransactions(OrderStatus status) {
        return transactionRepository.findAllByStatus(status)
                .stream().map(TransactionMapper::toTransactionDto).toList();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) throws Exception {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        OrderStatus status;
        Transaction transaction;

        // Pega o item da tabela
        if (transactionOptional.isPresent()) {
            transaction = transactionOptional.get();
            status = transaction.getStatus();
        } else {
            throw new Exception("Não foi possível achar a transação: " + id);
        }

        // Máquina de estados
        if (status == OrderStatus.CARRINHO_COMPRAS) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.EM_PROCESSAMENTO);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }

            // O cartão de crédito não aprova, ele só reprova se for inválido o cartão
        } else if (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.EM_TRANSITO);
                // Dá a baixa no estoque aqui
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.EM_TRANSITO) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.ENTREGUE);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                transaction.setStatus(OrderStatus.EM_TROCA);
                // Aparece um aviso para o cliente, pode ser uma lista de produtos que estão
                // nesse estado
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else {
            // Não faz nada nos outros estados que são dos pedidos
        }

        transactionRepository.save(transaction);
    }
}