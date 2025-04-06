package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.mapper.ProductCategoryMapper;
import org.fatec.esportiva.repository.ProductCategoryRepository;
import org.fatec.esportiva.request.ProductCategoryDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository pricingGroupRepository;

    public List<ProductCategoryDto> getProductCategories() {
        List<ProductCategoryDto> products = pricingGroupRepository.findAll().stream()
                .map(ProductCategoryMapper::toProductCategoryDto).toList();
        return products;
    }
}
