package org.fatec.esportiva.dto.request;

import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;

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
public class OrderDto {
    private Long id;

    @NotNull(message = "Status não pode ficar em branco")
    private OrderStatus status;

    @NotNull(message = "Quantidade de produtos em uma ordem de compra não pode ficar em branco")
    @Min(value = 0, message = "Quantidade de produtos em uma ordem de compra não pode ser negativa")
    private int quantity;

    @NotNull(message = "Toda ordem de compra deve estar associada a uma transação")
    private Transaction transaction;

    @NotNull(message = "Toda ordem de compra deve estar associada a um produto")
    private Product product;
}
