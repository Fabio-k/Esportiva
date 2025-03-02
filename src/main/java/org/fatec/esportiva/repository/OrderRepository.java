package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
