package org.fatec.esportiva.repository;

import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(OrderStatus status);

    Optional<Order> findByTransactionClientIdAndId(Long transactionClientId, Long id);
}
