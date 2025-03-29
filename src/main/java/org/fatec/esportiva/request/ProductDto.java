package org.fatec.esportiva.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.ProductCategory;
import org.fatec.esportiva.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Long id;

    @NotBlank(message = "Nome não pode ficar em branco")
    private String name;

    @NotNull(message = "Data de entrada não pode ficar em branco")
    @PastOrPresent(message = "Data de entrada pode ser até o presente momento")
    private LocalDate entryDate;

    @NotNull(message = "Quantidade no estoque não pode ficar em branco")
    @Min(value = 0, message = "Quantidade no estoque não pode ser negativa")
    private int stockQuantity;

    @NotNull(message = "Quantidade reservada pelos carrinhos de compra não pode ficar em branco")
    @Min(value = 0, message = "Quantidade reservada pelos carrinhos de compra não pode ser negativa")
    private int blockedQuantity;

    @NotNull(message = "Margem de lucro não pode ficar em branco")
    @Min(value = 0, message = "Margem de lucro deve ser maior que 0%")
    private float profitMargin;

    @NotNull(message = "Custo não pode ficar em branco")
    @Min(value = 0, message = "O custo não pode ser negativo")
    private BigDecimal costValue;

    @NotNull(message = "Categoria de desativação não pode ficar em branco")
    private ProductStatus inactivationCategory;

    // Pode ter justificativa vazia (Quando não está desativado, por exemplo)
    @NotNull(message = "A justificativa de desativação não pode ser nula, mas pode ser uma string vazia")
    private String inactivationJustification;

    @NotNull(message = "Referencia para uma categoria de inativação não pode ficar em branco")
    private Long pricingGroupId;

    @NotNull(message = "Referencia a uma categoria de produto não pode ficar em branco")
    @Default
    private List<ProductCategory> productCategory = new ArrayList<>();

    public String displayEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return entryDate.format(formatter);
    }
}
