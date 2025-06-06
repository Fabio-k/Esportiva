package org.fatec.esportiva.service.transaction.states;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.service.transaction.TransactionContext;
import org.fatec.esportiva.service.transaction.TransactionState;
import org.springframework.stereotype.Component;

/**
 * Handler para o estado {@link OrderStatus#ENTREGUE} de uma transação
 * <p>Altera o estado para {@link OrderStatus#EM_TROCA}</p>
 * <p>Não há como rejeitar a transição de entregue para em troca</p>
 */
@Component
public class TransactionDeliveredHandler implements TransactionState {
    @Override
    public void approve(Transaction transaction, TransactionContext context) {
        transaction.setStatus(OrderStatus.EM_TROCA);
        context.propagateStatusToOrder(transaction, true);
    }

    @Override
    public void reprove(Transaction transaction, TransactionContext context) {}
}
