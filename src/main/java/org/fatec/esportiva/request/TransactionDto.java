package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

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

    @NotBlank
    private int id;

    @PastOrPresent(message = "O produto somente pode ser comprado até a presente data!")
    @NotBlank
    private Date purchase_date;

    @NotBlank
    private List<Order> orders = new ArrayList<>();
}
