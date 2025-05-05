package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
