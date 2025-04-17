package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.request.TransactionDto;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.response.TransactionResponseDto;

@UtilityClass
public class TransactionMapper {
    public TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .status(transaction.getStatus())
                .purchaseDate(transaction.getPurchaseDate())
                .orders(OrderMapper.toOrderByStatusResponseDto(transaction.getOrders()))
                .build();
    }

    public Transaction toTransaction(TransactionDto transactionsDto) {
        return Transaction.builder()
                .purchaseDate(transactionsDto.getPurchaseDate())
                .status(transactionsDto.getStatus())
                .client(transactionsDto.getClient())
                .orders(transactionsDto.getOrders())
                .build();
    }

    public TransactionDto toTransactionDto(Transaction transactions) {
        return TransactionDto.builder()
                .id(transactions.getId())
                .purchaseDate(transactions.getPurchaseDate())
                .status(transactions.getStatus())
                .client(transactions.getClient())
                .orders(transactions.getOrders())
                .build();
    }
}
