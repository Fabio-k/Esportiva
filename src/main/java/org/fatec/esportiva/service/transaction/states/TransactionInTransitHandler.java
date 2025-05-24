package org.fatec.esportiva.service.transaction.states;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.transaction.TransactionContext;
import org.fatec.esportiva.service.transaction.TransactionStateHandler;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#EM_TRANSITO} de uma transação
 * se a descisão ({@code isApproved}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Altera o estado para {@link OrderStatus#ENTREGUE}</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <li>Produto que estava indo para o cliente, volta para o estoque</li>
 *         <li>Altera o estado para {@link OrderStatus#COMPRA_CANCELADA}</li>
 *     </li>
 * </ul>
 */
@Component
public class TransactionInTransitHandler implements TransactionStateHandler {
    @Override
    public void process(Transaction transaction, Boolean isApproved, TransactionContext context) {
        if (isApproved) {
            transaction.setStatus(OrderStatus.ENTREGUE);
            context.propagateStatusToOrder(transaction, true);
            return;
        }
        transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
        context.propagateStatusToOrder(transaction, false);
        context.refundSingleVoucher(transaction);
    }
}
