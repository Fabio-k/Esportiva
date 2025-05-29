package org.fatec.esportiva.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.product.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN p.categories c WHERE (:maxValue IS NULL OR p.costValue <= :maxValue) AND (:status IS NULL OR p.status = :status) AND (:category IS NULL OR c.name = :category) AND (:name is NULL OR LOWER(CAST(p.name AS text)) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Product> findWithFilter(String name, ProductStatus status, Integer maxValue, String category);

    @Query("SELECT p FROM Product p WHERE p.status = :status AND (p.stockQuantity - p.blockedQuantity) > 0")
    List<Product> findAvailableProducts(ProductStatus status);

    List<Product> findAllByStatus(ProductStatus status);

    @Query("""
            SELECT p FROM Product p
            JOIN p.categories c
            WHERE p.status = :status
            AND (p.stockQuantity - p.blockedQuantity) > 0
            AND c.id IN :categoriesIds
            AND p.id NOT IN :productsIds
            """)
    List<Product> findRecommendedProducts(ProductStatus status, Set<Long> categoriesIds, Set<Long> productsIds);

    Optional<Product> findByIdAndStatus(Long id, ProductStatus status);
}
