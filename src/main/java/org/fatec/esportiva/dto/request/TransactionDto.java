package org.fatec.esportiva.dto.request;

import lombok.*;
import lombok.Builder.Default;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private LocalDate purchaseDate;

    private OrderStatus status;

    @Default
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    private String client;

    public String displayEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return purchaseDate.format(formatter);
    }
}
