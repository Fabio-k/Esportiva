package org.fatec.esportiva.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;

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

    private OrderStatus status;

    private List<OrderResponseDto> orders = new ArrayList<>();

    public Boolean isInProcessing(){
        return status == OrderStatus.EM_PROCESSAMENTO;
    }

    public Boolean isInTransit(){
        return status == OrderStatus.EM_TRANSITO;
    }

    public Boolean isDelivered(){
        return status == OrderStatus.ENTREGUE;
    }
}
