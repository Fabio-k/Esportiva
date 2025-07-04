package org.fatec.esportiva.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.product.PricingGroup;
import org.fatec.esportiva.entity.product.ProductStatus;

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

    // Esse campo não tem validação porque ele é inserido pelo back-end
    // @NotNull(message = "Data de entrada não pode ficar em branco")
    // @PastOrPresent(message = "Data de entrada pode ser até o presente momento")
    private LocalDate entryDate;

    @NotNull(message = "Quantidade no estoque não pode ficar em branco")
    @Min(value = 0, message = "Quantidade no estoque não pode ser negativa")
    private int stockQuantity;

    @NotNull(message = "Quantidade reservada pelos carrinhos de compra não pode ficar em branco")
    @Min(value = 0, message = "Quantidade reservada pelos carrinhos de compra não pode ser negativa")
    private int blockedQuantity;

    @NotNull(message = "Custo não pode ficar em branco")
    @Min(value = 0, message = "O custo não pode ser negativo")
    private BigDecimal costValue;

    @NotNull(message = "Status não pode ficar em branco")
    private ProductStatus inactivationCategory;

    // Pode ter justificativa vazia (Quando não está desativado, por exemplo)
    @NotNull(message = "A justificativa de desativação não pode ser nula, mas pode ser uma string vazia")
    private String inactivationJustification;

    @NotNull(message = "A categoria de preços não pode ficar vazia")
    private PricingGroup pricingGroup;

    @NotNull(message = "A descrição do produto não pode estar vazia")
    private String description;

    @NotNull(message = "O caminho para a imagem não pode estar vazia")
    private String image;

    @Default
    private List<Long> productCategoryIds = new ArrayList<>();

    public String displayEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return entryDate.format(formatter);
    }
}
