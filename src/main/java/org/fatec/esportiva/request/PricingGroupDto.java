package org.fatec.esportiva.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class PricingGroupDto {
    private Long id;

    @NotNull(message = "Nome do grupo de preços não pode ficar em branco")
    private String name;

    @NotNull(message = "Margem de lucro não pode ficar em branco")
    @Min(value = 0, message = "Margem de lucro deve ser maior que 0%")
    private BigDecimal profitMargin;
}
