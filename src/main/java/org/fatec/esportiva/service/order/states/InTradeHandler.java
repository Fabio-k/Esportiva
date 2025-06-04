package org.fatec.esportiva.service.order.states;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.order.OrderItemHandlerContext;
import org.fatec.esportiva.service.order.OrderState;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#EM_TROCA} de um pedido
 * se a descisão ({@code decision}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Notifica que a troca do item foi aceita</li>
 *              <li>Altera o estado para {@link OrderStatus#TROCADO}</li>
 *              <li>Cria um voucher com o valor do produto</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <p>muda o estado para {@link OrderStatus#TROCA_RECUSADA}</p>
 *     </li>
 * </ul>
 */
//TODO alterar reembolso para ser equivanlente ao valor da data de compra
@Component
public class InTradeHandler implements OrderState {
    @Override
    public void approve(Order order, OrderItemHandlerContext context) {
        Client client = order.getTransaction().getClient();

        context.getNotificationService().notifyTradeAccepted(order);
        order.setStatus(OrderStatus.TROCADO);
        context.getExchangeVoucherService().createExchangeVoucher(client, order.getTotalPrice());
    }

    @Override
    public void reprove(Order order, OrderItemHandlerContext context) {
        order.setStatus(OrderStatus.TROCA_RECUSADA);
    }
}
