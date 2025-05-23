package org.fatec.esportiva.service.order.states;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.OrderItemHandlerContext;
import org.fatec.esportiva.service.order.OrderStatusHandler;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#ENTREGUE} de um pedido
 * se a descisão ({@code decision}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Solicita troca</li>
 *              <li>Altera o estado para {@link OrderStatus#EM_TROCA}</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <li>Altera o estado para {@link OrderStatus#TROCA_RECUSADA}</li>
 *     </li>
 * <ul/>
 */
@Component
public class DeliveredHandler implements OrderStatusHandler {
    @Override
    public void process(Order order, OrderItemHandlerContext context) {
        if (context.getIsApproved()) {
            order.setStatus(OrderStatus.EM_TROCA);
            return;
        }
        order.setStatus(OrderStatus.TROCA_RECUSADA);
    }
}
