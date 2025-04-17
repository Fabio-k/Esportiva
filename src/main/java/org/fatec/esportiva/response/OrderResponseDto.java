package org.fatec.esportiva.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.entity.enums.OrderStatus;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private ProductResponseDto product;
    private Integer quantity;
    private OrderStatus status;
    private Integer totalProductQuantity;

    public Boolean isSuccessfullyTraded(){
        return status == OrderStatus.TROCADO || status == OrderStatus.TROCA_FINALIZADA;
    }

    public Boolean isTradeRefused(){
        return status == OrderStatus.TROCA_RECUSADA;
    }

    public  Boolean isTradeAccepted(){
        return status == OrderStatus.EM_TROCA;
    }
}
