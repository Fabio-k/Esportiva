package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.repository.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
