package org.fatec.esportiva.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.entity.product.PricingGroup;
import org.fatec.esportiva.entity.product.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdminProductResponseDto {
    private Long id;

    private String name;
    private LocalDate entryDate;
    private int stockQuantity;
    private int blockedQuantity;
    private BigDecimal costValue;
    private BigDecimal salesPrice;
    private ProductStatus inactivationCategory;
    private String inactivationJustification;
    private String description;
    private String image;

    @Builder.Default
    private List<ProductCategoryResponseDto> categories = new ArrayList<>();
}
