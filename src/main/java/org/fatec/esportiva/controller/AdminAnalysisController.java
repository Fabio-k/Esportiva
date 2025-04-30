package org.fatec.esportiva.controller;

import org.fatec.esportiva.repository.ProductCategoryRepository;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/analysis")
public class AdminAnalysisController {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductService productService;

    @GetMapping
    public String inProcessing(Model model) {
        model.addAttribute("categories", productCategoryRepository.findAll());
        model.addAttribute("products", productService.getAllProducts());
        return "admin/analysis";
    }
}
