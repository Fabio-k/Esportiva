package org.fatec.esportiva.repository;

import org.fatec.esportiva.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
