package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.response.ProductResponseDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{id}")
    public String getProduct(Model model, @PathVariable Long id){
        ProductResponseDto product = productService.findProduct(id);
        model.addAttribute("product", product);
        return "products/show";
    }
}
