package org.fatec.esportiva.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

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
    private Long id;

    @NotBlank(message = "Nome não pode ficar em branco")
    private String product_name;

    @NotNull(message = "Data de entrada não pode ficar em branco")
    @PastOrPresent(message = "Data de entrada pode ser até o presente momento")
    private Date entry_date;

    @NotNull(message = "Quantidade no estoque não pode ficar em branco")
    @Min(value = 0, message = "Quantidade no estoque não pode ser negativa")
    private int stock_quantity;

    @NotNull(message = "Quantidade reservada pelos carrinhos de compra não pode ficar em branco")
    @Min(value = 0, message = "Quantidade reservada pelos carrinhos de compra não pode ser negativa")
    private int blocked_quantity;

    @NotNull(message = "Margem de lucro não pode ficar em branco")
    @Min(value = 0, message = "Margem de lucro deve ser maior que 0%")
    private float pricing_value;

    @NotNull(message = "Custo não pode ficar em branco")
    @Min(value = 0, message = "O custo não pode ser negativo")
    private float cost;

    // Pode ter justificativa vazia (Quando não está desativado, por exemplo)
    @NotNull(message = "A justificativa de desativação não pode ser nula, mas pode ser uma string vazia")
    private String deactivation_justification;

    @NotNull(message = "Categoria de desativação não pode ficar em branco")
    private ProductStatus deactivation_category;

    @NotNull(message = "Referencia para uma categoria de inativação não pode ficar em branco")
    private Long pricing_group_id;

    @NotNull(message = "Referencia a uma categoria de produto não pode ficar em branco")
    @Default
    private List<ProductCategory> product_category = new ArrayList<>();
}
