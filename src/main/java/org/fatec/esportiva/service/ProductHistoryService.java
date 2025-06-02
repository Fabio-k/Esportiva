package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.dto.projection.CategoryProductStateView;
import org.fatec.esportiva.dto.response.SalesHistoryResponseDto;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.repository.ProductHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    public SalesHistoryResponseDto getCategoryOrProductHistoryById(Long id, Boolean isCategory, LocalDate startDate, LocalDate endDate) {
        LocalDateTime endDateTime = null;
        if (endDate != null){
            endDateTime = endDate.atTime(LocalTime.MAX);
        }
        List<CategoryProductHistoryView> salesHistoryByDate =  productHistoryRepository.getCategoryOrProductHistoryById(id, isCategory, startDate, endDateTime, OrderStatus.getSalesReportStatus());
        List<CategoryProductStateView> salesHistoryByState = productHistoryRepository.getCategoryOrProductStateHistoryById(id, isCategory, startDate, endDateTime, OrderStatus.getSalesReportStatus());

        SalesHistoryResponseDto salesHistoryResponseDto = new SalesHistoryResponseDto();
        salesHistoryResponseDto.getSalesHistoryByDate().addAll(salesHistoryByDate);
        salesHistoryResponseDto.getSalesHistoryByState().addAll(salesHistoryByState);

        return salesHistoryResponseDto;
    }
}
