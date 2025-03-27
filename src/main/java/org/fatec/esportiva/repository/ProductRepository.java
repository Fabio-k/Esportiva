package org.fatec.esportiva.repository;

import java.util.List;

import org.fatec.esportiva.entity.Product;
import org.fatec.esportiva.entity.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE (:maxValue IS NULL OR p.costValue <= :maxValue) AND (:status IS NULL OR p.status = :status) AND (:name is NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Product> findWithFilter(String name, ProductStatus status, Integer maxValue);

    List<Product> findAllByStatus(ProductStatus status);
}
