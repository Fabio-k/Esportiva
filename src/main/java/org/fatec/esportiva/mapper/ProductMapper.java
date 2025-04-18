package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Product;

import org.fatec.esportiva.dto.request.ProductDto;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.dto.response.ProductResponseDto;

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
                .status(productDto.getInactivationCategory())
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
                .inactivationCategory(product.getStatus())
                .inactivationJustification(product.getInactivationJustification())
                .build();
    }

    public ProductResponseDto toProductResponseDto(Product product){
        int availableQuantity = product.getStockQuantity() - product.getBlockedQuantity();
        return new ProductResponseDto(product.getId(), availableQuantity, product.getName(),
                product.getPriceWithMargin(), product.getDescription(), product.getImage());
    }
}
