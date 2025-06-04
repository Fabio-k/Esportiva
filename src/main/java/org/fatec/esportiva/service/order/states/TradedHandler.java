package org.fatec.esportiva.service.order.states;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.OrderItemHandlerContext;
import org.fatec.esportiva.service.order.OrderState;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#TROCADO} de um pedido
 * se a descisão ({@code decision}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Altera o estado para {@link OrderStatus#TROCA_FINALIZADA}</li>
 *              <li>(RF0043): {@code isReturnStock} for {@code true} adiciona a quantidade trocada de volta ao estoque</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <p>muda o estado para {@link OrderStatus#TROCA_RECUSADA}</p>
 *     </li>
 * </ul>
 */
@Component
public class TradedHandler implements OrderState {
    @Override
    public void approve(Order order, OrderItemHandlerContext context) {
        order.setStatus(OrderStatus.TROCA_FINALIZADA);

        if (context.getIsReturnStock()) {
            order.getProduct().increaseStock(order.getQuantity());
        }
    }

    @Override
    public void reprove(Order order, OrderItemHandlerContext context) {
        order.setStatus(OrderStatus.TROCA_RECUSADA);
    }
}
