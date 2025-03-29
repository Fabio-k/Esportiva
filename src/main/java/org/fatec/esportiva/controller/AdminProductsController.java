package org.fatec.esportiva.controller;

import java.util.List;

import org.fatec.esportiva.entity.enums.ProductStatus;
import org.fatec.esportiva.request.ProductDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductsController {
    private final ProductService productService;

    @GetMapping
    public String getProducts(Model model,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "entryDate", required = false, defaultValue = "") String entryDate,
            @RequestParam(value = "stockQuantity", required = false, defaultValue = "1000") int stockQuantity,
            @RequestParam(value = "blockedQuantity", required = false, defaultValue = "1000") int blockedQuantity,
            @RequestParam(value = "profitMargin", required = false, defaultValue = "1000") float profitMargin,
            @RequestParam(value = "costValue", required = false, defaultValue = "1000") float costValue,
            @RequestParam(value = "entryDate", required = false) ProductStatus inactivationCategory) {
        List<ProductDto> products = productService.getProducts(name, costValue);
        model.addAttribute("products", products);
        return "admin/products/index";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        return "admin/products/new";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        return "admin/products/edit";
    }
}
