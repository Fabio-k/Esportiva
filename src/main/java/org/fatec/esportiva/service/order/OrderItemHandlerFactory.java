package org.fatec.esportiva.service.order;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.states.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OrderItemHandlerFactory {
    private final HashMap<OrderStatus, OrderStatusHandler> handlers = new HashMap<>();

    public OrderItemHandlerFactory(){
        handlers.put(OrderStatus.EM_PROCESSAMENTO, new InProcessingHandler());
        handlers.put(OrderStatus.EM_TRANSITO, new InTransitProcessingHandler());
        handlers.put(OrderStatus.ENTREGUE, new DeliveredHandler());
        handlers.put(OrderStatus.EM_TROCA, new InTradeHandler());
        handlers.put(OrderStatus.TROCADO, new TradedHandler());

    }

    public OrderStatusHandler getHandler(OrderStatus status){
        OrderStatusHandler handler = handlers.get(status);
        if(handler == null){
            throw new IllegalArgumentException("Nenhum handler configurado para o status: " + status);
        }
        return handler;
    }
}
