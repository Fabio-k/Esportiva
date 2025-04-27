package org.fatec.esportiva.repository;

import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    @Query("SELECT o.purchaseDate AS purchaseDate, SUM(o.totalOrders) AS totalOrders FROM ProductHistory o WHERE (:isCategory = true AND o.categoryId = :id) OR (:isCategory = false AND o.productId = :id) GROUP BY o.purchaseDate")
    List<CategoryProductHistoryView> getCategoryOrProductHistoryById(Long id, Boolean isCategory);
}
