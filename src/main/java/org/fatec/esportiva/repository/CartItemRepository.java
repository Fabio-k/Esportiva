package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
