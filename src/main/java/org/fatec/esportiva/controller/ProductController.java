package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.ProductResponseDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String findProductByName(Model model, @RequestParam(required = false, name = "name") String name,
                                    @RequestParam(required = false, name = "maxValue") Integer maxValue,
                                    @RequestParam(required = false, name = "category") String category
    ){
        List<ProductResponseDto> products = productService.findProductsSummary(name, maxValue, category);
        model.addAttribute("products", products);
        return "products/search";
    }

    @GetMapping("/search/results")
    public String findProductByFilters(Model model, @RequestParam(required = false, name = "name") String name,
                                       @RequestParam(required = false, name = "maxValue") Integer maxValue,
                                       @RequestParam(required = false, name = "category") String category){

        List<ProductResponseDto> products = productService.findProductsSummary(name, maxValue, category);
        model.addAttribute("products", products);
        return "fragments/searchResults";
    }
}
