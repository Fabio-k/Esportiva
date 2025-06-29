package org.fatec.esportiva.service.transaction;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.transaction.states.TransactionInProcessingHandler;
import org.fatec.esportiva.service.transaction.states.TransactionInTransitHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TransactionStateFactory {
    private final HashMap<OrderStatus, TransactionState> handlers = new HashMap<>();

    public TransactionStateFactory(TransactionInProcessingHandler transactionInProcessingHandler, TransactionInTransitHandler transactionInTransitHandler){
        handlers.put(OrderStatus.EM_PROCESSAMENTO, transactionInProcessingHandler);
        handlers.put(OrderStatus.EM_TRANSITO, transactionInTransitHandler);
    }

    public TransactionState getHandler(OrderStatus status){
        if(status == null){
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        TransactionState handler = handlers.get(status);
        if(handler == null){
            throw new IllegalArgumentException("Nenhum handler encontrado para o status: " + status);
        }

        return handler;
    }

}
