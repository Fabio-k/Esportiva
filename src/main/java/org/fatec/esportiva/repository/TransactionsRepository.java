package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
