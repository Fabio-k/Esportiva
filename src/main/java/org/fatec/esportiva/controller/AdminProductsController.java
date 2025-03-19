package org.fatec.esportiva.controller;

import java.util.List;

import org.fatec.esportiva.request.ProductDto;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductsController {
    private final ProductService productService;

    @GetMapping
    public String getClients(Model model,
            @RequestParam(value = "name", required = false) String name) {

        List<ProductDto> products = productService.getProducts(name);
        model.addAttribute("products", products);
        return "admin/products/index";
    }
}
