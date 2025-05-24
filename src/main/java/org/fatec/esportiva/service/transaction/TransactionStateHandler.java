package org.fatec.esportiva.service.transaction;

import org.fatec.esportiva.entity.Transaction;

public interface TransactionStateHandler {
    void process(Transaction transaction, Boolean isApproved, TransactionContext context);
}
