package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.repository.ProductHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    public List<CategoryProductHistoryView> getCategoryOrProductHistoryById(Long id, Boolean isCategory, LocalDate startDate, LocalDate endDate) {
        return productHistoryRepository.getCategoryOrProductHistoryById(id, isCategory, startDate, endDate, OrderStatus.getSalesReportStatus());
    }
}
