package org.fatec.esportiva.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import org.fatec.esportiva.entity.ProductCategory;
import org.fatec.esportiva.entity.enums.ProductStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    @NotBlank
    private int id;

    @Min(value = 0, message = "A quantidade no estoque não pode ser negativa!")
    @NotBlank
    private int stock_quantity;

    @Min(value = 0, message = "A quantidade no reservada não pode ser negativa!")
    @NotBlank
    private int blocked_quantity;

    @Min(value = 0, message = "O valor da margem de precificação deve ser maior que 0%!")
    @NotBlank
    private float pricing_value;

    @NotBlank
    private String deactivation_justification;

    @NotBlank
    private ProductStatus deactivation_category;

    @Min(value = 0, message = "O custo do produto não pode ser negativo!")
    @NotBlank
    private float cost;

    @PastOrPresent(message = "O produto somente pode ser inserido no sistema até o presente momento")
    @NotBlank
    private Date entry_date;

    @NotBlank
    private String product_name;

    @NotBlank
    private int pricing_group_id;

    @NotBlank
    private List<ProductCategory> product_category = new ArrayList<>();
}
