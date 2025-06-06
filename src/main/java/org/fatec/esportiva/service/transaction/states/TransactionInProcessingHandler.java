package org.fatec.esportiva.service.transaction.states;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.transaction.TransactionContext;
import org.fatec.esportiva.service.transaction.TransactionState;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#EM_PROCESSAMENTO} de uma transação
 * se a descisão ({@code isApproved}) for:
 * <ul>
 *     <li>{@code true} Aprovado:
 *          <ul>
 *              <li>Altera o estado para {@link OrderStatus#EM_TRANSITO}</li>
 *          </ul>
 *     </li>
 *     <li>{@code false} Rejeitado:
 *         <li>Reembolsa o cliente com um voucher</li>
 *         <li>Altera o estado para {@link OrderStatus#COMPRA_CANCELADA}</li>
 *     </li>
 * </ul>
 */
@Component
public class TransactionInProcessingHandler implements TransactionState {
    @Override
    public void approve(Transaction transaction, TransactionContext context) {
        transaction.setStatus(OrderStatus.EM_TRANSITO);
        context.propagateStatusToOrder(transaction, true);
    }

    @Override
    public void reprove(Transaction transaction, TransactionContext context) {
        transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
        context.propagateStatusToOrder(transaction, false);
        context.refundSingleVoucher(transaction);
    }
}
