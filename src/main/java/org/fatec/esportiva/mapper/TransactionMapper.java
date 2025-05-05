package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.dto.request.TransactionDto;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.dto.response.TransactionResponseDto;

@UtilityClass
public class TransactionMapper {
    public TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .status(transaction.getStatus())
                .purchaseDate(transaction.getPurchaseDate().toLocalDate())
                .orders(OrderMapper.toOrderByStatusResponseDto(transaction.getOrders()))
                .build();
    }

    public TransactionDto toTransactionDto(Transaction transactions) {
        return TransactionDto.builder()
                .id(transactions.getId())
                .purchaseDate(transactions.getPurchaseDate().toLocalDate())
                .status(transactions.getStatus())
                .client(transactions.getClient())
                .orders(transactions.getOrders())
                .build();
    }
}
