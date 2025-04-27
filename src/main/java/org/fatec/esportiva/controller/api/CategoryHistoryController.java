package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.projection.CategoryProductHistoryView;
import org.fatec.esportiva.repository.ProductHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryHistoryController {
    private final ProductHistoryRepository categoryHistoryRepository;

    @GetMapping("/api/history")
    public ResponseEntity<List<CategoryProductHistoryView>> categories(@RequestParam Long id, @RequestParam Boolean isCategory){
        return ResponseEntity.ok(categoryHistoryRepository.getCategoryOrProductHistoryById(id, isCategory));
    }
}
