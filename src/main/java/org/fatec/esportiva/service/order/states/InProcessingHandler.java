package org.fatec.esportiva.service.order.states;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.OrderItemHandlerContext;
import org.fatec.esportiva.service.order.OrderState;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#EM_PROCESSAMENTO} de um pedido
 * se a descisão ({@code decision}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Altera o estado para {@link OrderStatus#EM_TRANSITO}</li>
 *              <li>Dá baixa no estoque</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <p>muda o estado para {@link OrderStatus#COMPRA_CANCELADA} e reembolsa a compra</p>
 *     </li>
 * </ul>
 */
@Component
public class InProcessingHandler implements OrderState {

    @Override
    public void approve(Order order, OrderItemHandlerContext context) {
        order.setStatus(OrderStatus.EM_TRANSITO);
        order.getProduct().processSoldStock(order.getQuantity());
    }

    @Override
    public void reprove(Order order, OrderItemHandlerContext context) {
        order.setStatus(OrderStatus.COMPRA_CANCELADA);
    }
}
