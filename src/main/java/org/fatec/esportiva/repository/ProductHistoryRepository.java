package org.fatec.esportiva.repository;

import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    @Query("""
            SELECT o.purchaseDate AS purchaseDate, SUM(o.totalOrders) AS totalOrders
            FROM ProductHistory o
            WHERE ((:isCategory = true AND o.categoryId = :id) OR (:isCategory = false AND o.productId = :id))
            AND (o.purchaseDate >= COALESCE(:startDate, o.purchaseDate))
            AND (o.purchaseDate <= COALESCE(:endDate, o.purchaseDate))
            GROUP BY o.purchaseDate""") //Encontrar motivo de :startDate IS NULL dar erro
    List<CategoryProductHistoryView> getCategoryOrProductHistoryById(Long id, Boolean isCategory, LocalDate startDate, LocalDate endDate);
}
