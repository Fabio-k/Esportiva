package org.fatec.esportiva.repository;

import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.dto.projection.CategoryProductStateView;
import org.fatec.esportiva.entity.ProductHistory;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
        @Query("""
                        SELECT o.purchaseDate AS purchaseDate, SUM(o.totalOrders) AS totalQuantity
                        FROM ProductHistory o
                        WHERE (o.status IN :allowedStatus)
                        AND ((:isCategory = true AND o.categoryId = :id) OR (:isCategory = false AND o.productId = :id))
                        AND (o.purchaseDate >= :startDate)
                        AND (o.purchaseDate < :endDate)
                        GROUP BY o.purchaseDate
                        HAVING SUM(o.totalOrders) > 0
                        """) // Encontrar motivo de :startDate IS NULL dar erro
        List<CategoryProductHistoryView> getCategoryOrProductHistoryById(Long id, Boolean isCategory,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                        @Param("allowedStatus") List<OrderStatus> allowedStatus);

        @Query("""
                        SELECT o.state AS state, SUM(o.totalOrders) AS totalQuantity
                        FROM ProductHistory o
                        WHERE (o.status IN :allowedStatus)
                        AND ((:isCategory = true AND o.categoryId = :id) OR (:isCategory = false AND o.productId = :id))
                        AND (o.purchaseDate >= :startDate)
                        AND (o.purchaseDate < :endDate)
                        GROUP BY o.state
                        HAVING SUM(o.totalOrders) > 0
                        """)
        List<CategoryProductStateView> getCategoryOrProductStateHistoryById(Long id, Boolean isCategory,
                        @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
                        @Param("allowedStatus") List<OrderStatus> allowedStatus);
}
