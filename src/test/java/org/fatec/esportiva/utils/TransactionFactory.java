package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.enums.OrderStatus;

import java.util.List;

public class TransactionFactory {

    public static Transaction defaultTransaction(Cep cep){

        return Transaction.builder()
                .cep(cep)
                .orders(List.of())
                .addressNumber("111")
                .status(OrderStatus.EM_PROCESSAMENTO)
                .build();
    }
}
