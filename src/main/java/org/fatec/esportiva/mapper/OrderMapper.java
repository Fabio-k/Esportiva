package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.dto.request.OrderDto;

import org.fatec.esportiva.dto.response.OrderByStatusResponseDto;
import org.fatec.esportiva.dto.response.OrderResponseDto;
import org.fatec.esportiva.entity.enums.OrderStatus;

import java.util.List;
import java.util.Objects;

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

    public OrderResponseDto toOrderResponseDto(Order order){
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .product(ProductMapper.toProductResponseDto(order.getProduct()))
                .quantity(order.getQuantity())
                .build();
    }

    public OrderByStatusResponseDto toOrderByStatusResponseDto(List<Order> orders){
        OrderByStatusResponseDto orderByStatusResponseDto = new OrderByStatusResponseDto();
        OrderResponseDto orderResponseDto;

        for(Order order : orders){
             orderResponseDto = toOrderResponseDto(order);

            if(order.isInDeliveryProcess() || order.getStatus() == OrderStatus.COMPRA_CANCELADA){
                orderByStatusResponseDto.getDeliveredOrders().add(orderResponseDto);
                orderResponseDto.setTotalProductQuantity(order.getQuantity());

            } else if (order.isBeingTraded()) {
                orderByStatusResponseDto.getTradedOrders().add(orderResponseDto);
            }
        }

        for(OrderResponseDto tradedOrder : orderByStatusResponseDto.getTradedOrders()){
            for(OrderResponseDto deliveredOrder : orderByStatusResponseDto.getDeliveredOrders()){
                if (Objects.equals(deliveredOrder.getProduct().id(), tradedOrder.getProduct().id())){
                    deliveredOrder.setTotalProductQuantity(deliveredOrder.getTotalProductQuantity() + tradedOrder.getQuantity());
                }
            }
        }

        return orderByStatusResponseDto;
    }
}
