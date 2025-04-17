package org.fatec.esportiva.response;

import lombok.*;

import org.fatec.esportiva.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class TransactionResponseDto {
    private Long id;

    private LocalDate purchaseDate;

    private OrderStatus status;

    private OrderByStatusResponseDto orders;

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
