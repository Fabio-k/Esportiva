package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findTopByCartIdOrderByInclusionTimeAsc(Long cartId);
}
