package org.fatec.esportiva.service.transaction;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.transaction.states.TransactionDeliveredHandler;
import org.fatec.esportiva.service.transaction.states.TransactionInProcessingHandler;
import org.fatec.esportiva.service.transaction.states.TransactionInTransitHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TransactionStateFactory {
    private final HashMap<OrderStatus, TransactionStateHandler> handlers = new HashMap<>();

    public TransactionStateFactory(TransactionInProcessingHandler transactionInProcessingHandler, TransactionInTransitHandler transactionInTransitHandler, TransactionDeliveredHandler transactionDeliveredHandler){
        handlers.put(OrderStatus.EM_PROCESSAMENTO, transactionInProcessingHandler);
        handlers.put(OrderStatus.EM_TRANSITO, transactionInTransitHandler);
        handlers.put(OrderStatus.ENTREGUE, transactionDeliveredHandler);
    }

    public TransactionStateHandler getHandler(OrderStatus status){
        TransactionStateHandler handler = handlers.get(status);
        if(handler == null){
            throw new IllegalArgumentException("Nenhum handler encontrado para o status: " + status);
        }

        return handler;
    }

}
