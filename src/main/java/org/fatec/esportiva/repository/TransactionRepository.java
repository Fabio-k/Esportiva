package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findAllByClientOrderByPurchaseDateDesc(Client client);
}
