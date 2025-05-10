package org.fatec.esportiva.controller;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.ProductResponseDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    @GetMapping
    public String getHome(Model model){
        List<ProductResponseDto> products = productService.getAllAvailableProducts();
        model.addAttribute("products", products);
        model.addAttribute("recommendedProducts", productService.getRecommendedProducts());
        return "products/index";
    }
}
