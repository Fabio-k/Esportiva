package org.fatec.esportiva.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class TransactionResponseDto {
    private Long id;

    private LocalDate purchaseDate;

    private List<OrderResponseDto> orders = new ArrayList<>();
}
