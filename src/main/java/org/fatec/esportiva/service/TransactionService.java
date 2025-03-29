package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.TransactionsRepository;
import org.fatec.esportiva.request.TransactionDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionsRepository transactionRepository;

    public List<TransactionDto> getTransactions(OrderStatus status) {
        return transactionRepository.findAllByStatus(status)
                .stream().map(TransactionMapper::toTransactionDto).toList();
    }
}