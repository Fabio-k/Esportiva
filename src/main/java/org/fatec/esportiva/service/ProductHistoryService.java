package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.dto.projection.CategoryProductStateView;
import org.fatec.esportiva.dto.response.SalesHistoryResponseDto;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.exception.ApiException;
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
        LocalDateTime endDateTime = null, startDateTime = null;
        if(startDate != null && endDate != null && startDate.isAfter(endDate)) throw new ApiException("intervalo de datas inválido");
        startDateTime = startDate != null ?  startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
        endDateTime = endDate != null ? LocalDateTime.of(endDate.plusDays(1), LocalTime.MIN) : LocalDateTime.of(2300, 1, 1, 0, 0);

        // Obtém os dados do BD para encaminhar ao front-end
        List<CategoryProductHistoryView> salesHistoryByDate =  productHistoryRepository.getCategoryOrProductHistoryById(id, isCategory, startDateTime , endDateTime, OrderStatus.getSalesReportStatus());
        List<CategoryProductStateView> salesHistoryByState = productHistoryRepository.getCategoryOrProductStateHistoryById(id, isCategory, startDateTime, endDateTime, OrderStatus.getSalesReportStatus());
        SalesHistoryResponseDto salesHistoryResponseDto = new SalesHistoryResponseDto();
        salesHistoryResponseDto.getSalesHistoryByDate().addAll(salesHistoryByDate);
        salesHistoryResponseDto.getSalesHistoryByState().addAll(salesHistoryByState);

        return salesHistoryResponseDto;
    }
}
