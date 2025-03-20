package org.fatec.esportiva.repository;

import java.util.List;

import org.fatec.esportiva.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE (:name is NULL or p.name LIKE :name)")
    List<Product> findWithFilter(String name);
}
