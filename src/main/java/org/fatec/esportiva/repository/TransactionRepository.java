package org.fatec.esportiva.repository;

import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByStatus(OrderStatus status);
    List<Transaction> findAllByClientOrderByPurchaseDateDesc(Client client);
    Optional<Transaction> findByClientAndIdAndStatus(Client client, Long id, OrderStatus status);
}
