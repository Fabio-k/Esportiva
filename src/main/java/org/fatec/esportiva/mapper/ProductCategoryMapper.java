package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.ProductCategory;
import org.fatec.esportiva.request.ProductCategoryDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductCategoryMapper {
    public ProductCategory toProductCategory(ProductCategoryDto productCategoryDto) {
        return ProductCategory.builder()
                .name(productCategoryDto.getName())
                .build();
    }

    public ProductCategoryDto toProductCategoryDto(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .build();
    }
}
