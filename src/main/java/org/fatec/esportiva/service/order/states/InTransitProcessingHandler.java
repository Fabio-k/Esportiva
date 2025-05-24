package org.fatec.esportiva.service.order.states;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.OrderItemHandlerContext;
import org.fatec.esportiva.service.order.OrderStatusHandler;

/**
 * Handler para o estado {@link OrderStatus#EM_TRANSITO} de um pedido
 * se a descisão ({@code decision}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Vai para a casa do cliente</li>
 *              <li>Altera o estado para {@link OrderStatus#ENTREGUE}</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <li>Produto que estava indo para o cliente, volta para o estoque</li>
 *         <li>muda o estado para {@link OrderStatus#COMPRA_CANCELADA} e reembolsa a compra</li>
 *     </li>
 * </ul>
 */
public class InTransitProcessingHandler implements OrderStatusHandler {
    @Override
    public void process(Order order, OrderItemHandlerContext context) {
        if (context.getIsApproved()) {
            order.setStatus(OrderStatus.ENTREGUE);
            return;
        }
        order.getProduct().setStockQuantity(order.getProduct().getStockQuantity() + order.getQuantity());
        order.setStatus(OrderStatus.COMPRA_CANCELADA);
    }
}
