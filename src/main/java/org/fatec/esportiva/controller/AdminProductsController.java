package org.fatec.esportiva.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.Product;
import org.fatec.esportiva.entity.enums.ProductStatus;
import org.fatec.esportiva.request.ProductCategoryDto;
import org.fatec.esportiva.request.ProductDto;
import org.fatec.esportiva.service.PricingGroupService;
import org.fatec.esportiva.service.ProductCategoryService;
import org.fatec.esportiva.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductsController {
    private final ProductService productService;
    private final PricingGroupService pricingGroupService;
    private final ProductCategoryService productCategoryService;

    @GetMapping
    public String getProducts(Model model,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "entryDate", required = false, defaultValue = "") String entryDate,
            @RequestParam(value = "stockQuantity", required = false, defaultValue = "1000") int stockQuantity,
            @RequestParam(value = "blockedQuantity", required = false, defaultValue = "1000") int blockedQuantity,
            @RequestParam(value = "profitMargin", required = false, defaultValue = "1000") float profitMargin,
            @RequestParam(value = "costValue", required = false, defaultValue = "1000") int costValue,
            @RequestParam(value = "inactivationCategory", required = false) ProductStatus inactivationCategory,
            @RequestParam(value = "category", required = false) String category) {
        List<ProductDto> products = productService.getProducts(name, inactivationCategory, costValue,
                category);
        model.addAttribute("products", products);
        return "admin/products/index";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("formAction", "/admin/products/save");
        model.addAttribute("pricingGroups", pricingGroupService.getPrincingGroups());
        model.addAttribute("productCategories", productCategoryService.getProductCategories());
        if (!model.containsAttribute("product")) {
            ProductDto productDto = new ProductDto();
            productDto.getProductCategory().add(new ProductCategoryDto());
            model.addAttribute("product", productDto);
        }
        return "admin/products/new";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/products/new";
        }

        // Define a data atual de cadastro/atualização
        productDto.setEntryDate(LocalDate.now());

        productService.save(productDto);
        return "redirect:/admin/products";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Product> product = productService.findProduct2(id);
        productService.deleteClient(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        return "admin/products/edit";
    }
}
