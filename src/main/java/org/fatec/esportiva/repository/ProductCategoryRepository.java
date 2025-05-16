package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByIdIn(List<Long> ids);
}
