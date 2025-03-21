package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Product;

import org.fatec.esportiva.request.ProductDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .entryDate(productDto.getEntryDate())
                .stockQuantity(productDto.getStockQuantity())
                .blockedQuantity(productDto.getBlockedQuantity())
                .profitMargin(productDto.getProfitMargin())
                .costValue(productDto.getCostValue())
                .inactivationCategory(productDto.getInactivationCategory())
                .inactivationJustification(productDto.getInactivationJustification())
                .build();
    }

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .entryDate(product.getEntryDate())
                .stockQuantity(product.getStockQuantity())
                .blockedQuantity(product.getBlockedQuantity())
                .profitMargin(product.getProfitMargin())
                .costValue(product.getCostValue())
                .inactivationCategory(product.getInactivationCategory())
                .inactivationJustification(product.getInactivationJustification())
                .build();
    }
}
