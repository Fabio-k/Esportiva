package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Long id;

    @NotNull(message = "A compra do carrinho não pode ter data em branco")
    @PastOrPresent(message = "A compra do carrinho somente pode ser efetuada até o presente momento")
    private Date purchase_date;

    @NotNull(message = "Todo carrinho tem pelo menos um pedido associado")
    @Default
    private List<Order> orders = new ArrayList<>();
}
