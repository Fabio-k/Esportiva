package org.fatec.esportiva.request;

import org.fatec.esportiva.entity.Client;

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
public class ExchangeVoucherDto {
    private Long id;

    @NotNull(message = "Valor do cupom de desconto não pode ficar em branco")
    @Min(value = 0, message = "Valor do cupom de desconto não pode ser negativo")
    private float value;

    @NotNull(message = "Quantidade de cupons ficar em branco")
    @Min(value = 0, message = "Quantidade no estoque não pode ser negativa")
    private int quantity;

    @NotNull(message = "Todo cupom de desconto deve estar associado a um cliente")
    private Client client;
}
