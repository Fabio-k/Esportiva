package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private LocalDate purchaseDate;

    @NotNull(message = "Status não pode ficar em branco")
    private OrderStatus status;

    @NotNull(message = "Todo carrinho tem pelo menos um pedido associado")
    @Default
    private List<Order> orders = new ArrayList<>();

    @NotNull(message = "Toda transação deve estar associada a um cliente")
    private Client client;

    public String displayEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return purchaseDate.format(formatter);
    }
}
