package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.dto.response.SalesHistoryResponseDto;
import org.fatec.esportiva.service.ProductHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SalesHistoryController {
    private final ProductHistoryService productHistoryService;

    @GetMapping("/api/sales-history")
    public ResponseEntity<SalesHistoryResponseDto> categories(@RequestParam Long id, @RequestParam Boolean isCategory, @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate){
        return ResponseEntity.ok(productHistoryService.getCategoryOrProductHistoryById(id, isCategory, startDate, endDate));
    }
}
