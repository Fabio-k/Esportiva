package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.product.Product;

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
                .pricingGroup(productDto.getPricingGroup())
                .costValue(productDto.getCostValue())
                .status(productDto.getInactivationCategory())
                .inactivationJustification(productDto.getInactivationJustification())
                .description(productDto.getDescription())
                .build();
    }

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .entryDate(product.getEntryDate())
                .stockQuantity(product.getStockQuantity())
                .blockedQuantity(product.getBlockedQuantity())
                .profitMargin(product.getPricingGroup().getProfitMargin()) // Facilidade para obter a margem de lucro
                .pricingGroup(product.getPricingGroup())
                .costValue(product.getCostValue())
                .inactivationCategory(product.getStatus())
                .inactivationJustification(product.getInactivationJustification())
                .description(product.getDescription())
                .build();
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        int availableQuantity = product.getStockQuantity() - product.getBlockedQuantity();
        return new ProductResponseDto(product.getId(), availableQuantity, product.getName(),
                product.getPriceWithMargin(), product.getDescription(), product.getImage());
    }
}
