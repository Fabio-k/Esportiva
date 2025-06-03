package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.product.PricingGroup;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.product.ProductCategory;
import org.fatec.esportiva.entity.product.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProductFactory {
    public static Product defaultProduct(){
        Product product = Product.builder()
                .image("imagem")
                .inactivationJustification("")
                .status(ProductStatus.ATIVO)
                .name("Bola")
                .description("Uma bola")
                .costValue(BigDecimal.valueOf(42))
                .stockQuantity(10)
                .blockedQuantity(0)
                .entryDate(LocalDate.now())
                .build();

        PricingGroup pricingGroup = PricingGroupFactory.defaultPricingGroup(product);
        ProductCategory productCategory = ProductCategoryFactory.defaultProductCategory(product);

        product.setPricingGroup(pricingGroup);
        product.setCategories(List.of(productCategory));

        return product;
    }
}
