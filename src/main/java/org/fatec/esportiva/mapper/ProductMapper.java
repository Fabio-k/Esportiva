package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Product;
import org.fatec.esportiva.request.ProductDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public Product toProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .build();
    }

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }
}
