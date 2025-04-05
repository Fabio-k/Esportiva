package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.response.OrderResponseDto;

@UtilityClass
public class OrderMapper {
    public OrderResponseDto toDto(Order order){
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .product(ProductMapper.toProductResponseDto(order.getProduct()))
                .quantity(order.getQuantity())
                .build();
    }
}
