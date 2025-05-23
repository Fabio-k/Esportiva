package org.fatec.esportiva.service.order;

import org.fatec.esportiva.entity.Order;

public interface OrderStatusHandler {
    void process(Order order, OrderItemHandlerContext context);
}
