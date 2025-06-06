package org.fatec.esportiva.service.order;

import org.fatec.esportiva.entity.Order;

public interface OrderState {
    void approve(Order order, OrderItemHandlerContext context);
    void reprove(Order order, OrderItemHandlerContext context);
}
