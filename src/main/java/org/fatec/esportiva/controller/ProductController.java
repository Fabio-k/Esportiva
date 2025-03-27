package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.response.ProductResponseDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search")
    public String findProductByName(Model model, @RequestParam(required = true, name = "name") String name,
        @RequestParam(required = false, name = "maxValue") Integer maxValue
    ){
        List<ProductResponseDto> products = productService.findProductsSummary(name, maxValue);
        model.addAttribute("products", products);
        return "products/search";
    }
}
