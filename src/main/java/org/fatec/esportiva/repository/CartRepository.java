package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCreatedAtBefore(LocalDateTime createdAt);
    List<Cart> findByCreatedAtBeforeAndIsNotifiedFalse(LocalDateTime createdAt);
}

