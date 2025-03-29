package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.request.TransactionDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMapper {
    public Transaction toTransaction(TransactionDto transactionsDto) {
        return Transaction.builder()
                .purchaseDate(transactionsDto.getPurchaseDate())
                .status(transactionsDto.getStatus())
                .build();
    }

    public TransactionDto toTransactionDto(Transaction transactions) {
        return TransactionDto.builder()
                .id(transactions.getId())
                .purchaseDate(transactions.getPurchaseDate())
                .status(transactions.getStatus())
                .build();
    }
}
