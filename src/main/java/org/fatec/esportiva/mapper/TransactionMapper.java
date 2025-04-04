package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.response.TransactionResponseDto;

@UtilityClass
public class TransactionMapper {
    public TransactionResponseDto toDto(Transaction transaction){
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .purchaseDate(transaction.getPurchaseDate())
                .orders(transaction.getOrders().stream().map(OrderMapper::toDto).toList())
                .build();
    }
}
