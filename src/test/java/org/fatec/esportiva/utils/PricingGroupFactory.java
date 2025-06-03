package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.product.PricingGroup;
import org.fatec.esportiva.entity.product.Product;

import java.math.BigDecimal;
import java.util.List;

public class PricingGroupFactory {
    public static PricingGroup defaultPricingGroup(Product product){
        return PricingGroup.builder()
                .name("Alto")
                .profitMargin(BigDecimal.valueOf(20))
                .products(List.of(product))
                .build();
    }
}
