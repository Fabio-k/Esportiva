package org.fatec.esportiva.service.order;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.states.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OrderItemHandlerFactory {
    private final HashMap<OrderStatus, OrderState> handlers = new HashMap<>();

    public OrderItemHandlerFactory(InProcessingHandler inProcessingHandler, InTransitProcessingHandler inTransitProcessingHandler, DeliveredHandler deliveredHandler, InTradeHandler inTradeHandler ,TradedHandler tradedHandler){
        handlers.put(OrderStatus.EM_PROCESSAMENTO, inProcessingHandler);
        handlers.put(OrderStatus.EM_TRANSITO, inTransitProcessingHandler);
        handlers.put(OrderStatus.ENTREGUE, deliveredHandler);
        handlers.put(OrderStatus.EM_TROCA, inTradeHandler);
        handlers.put(OrderStatus.TROCADO, tradedHandler);

    }

    public OrderState getHandler(OrderStatus status){
        OrderState handler = handlers.get(status);
        if(handler == null){
            throw new IllegalArgumentException("Nenhum handler configurado para o status: " + status);
        }
        return handler;
    }
}
