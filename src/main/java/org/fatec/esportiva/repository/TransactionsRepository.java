package org.fatec.esportiva.repository;

import java.util.List;

import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByStatus(OrderStatus status);
}
