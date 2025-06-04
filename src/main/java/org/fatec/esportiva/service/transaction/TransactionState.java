package org.fatec.esportiva.service.transaction;

import org.fatec.esportiva.entity.Transaction;

public interface TransactionState {
    void approve(Transaction transaction, TransactionContext context);
    void reprove(Transaction transaction, TransactionContext context);
}
