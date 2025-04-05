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
}
