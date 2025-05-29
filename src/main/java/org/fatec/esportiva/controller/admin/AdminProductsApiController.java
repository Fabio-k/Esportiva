package org.fatec.esportiva.controller.admin;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.AdminProductResponseDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/api/products")
@RequiredArgsConstructor
public class AdminProductsApiController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<AdminProductResponseDto> getProduct(@PathVariable Long id){
        AdminProductResponseDto product = productService.findProductToAdminResponseDto(id);
        return ResponseEntity.ok(product);
    }
}
