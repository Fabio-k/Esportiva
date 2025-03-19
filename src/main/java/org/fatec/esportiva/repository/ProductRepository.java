package org.fatec.esportiva.repository;

import java.util.List;

import org.fatec.esportiva.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT c FROM Product c WHERE (:name is NULL or c.name LIKE :name)")
    List<Product> findWithFilter(String name);
}
