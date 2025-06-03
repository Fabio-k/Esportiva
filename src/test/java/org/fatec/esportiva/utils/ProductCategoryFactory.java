package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.product.ProductCategory;

import java.util.List;

public class ProductCategoryFactory {
    public static ProductCategory defaultProductCategory(Product product){
        return ProductCategory.builder()
                .name("Esportes em time")
                .products(List.of(product))
                .build();
    }
}
