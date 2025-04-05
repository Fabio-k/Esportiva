package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.request.OrderDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {
    public Order toOrder(OrderDto productDto) {
        return Order.builder()
                .status(productDto.getStatus())
                .quantity(productDto.getQuantity())
                .transaction(productDto.getTransaction())
                .product(productDto.getProduct())
                .build();
    }

    public OrderDto toOrderDto(Order product) {
        return OrderDto.builder()
                .id(product.getId())
                .status(product.getStatus())
                .quantity(product.getQuantity())
                .transaction(product.getTransaction())
                .product(product.getProduct())
                .build();
    }
}
