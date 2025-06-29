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
                .build();
    }

    public OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .quantity(order.getQuantity())
                .transactionId(order.getTransaction().getId())
                .client(order.getTransaction().getClient().getName())
                .purchaseDate(order.getTransaction().getPurchaseDate().toLocalDate())
                .productName(order.getProduct().getName())
                .build();
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .product(ProductMapper.toProductResponseDto(order.getProduct()))
                .quantity(order.getQuantity())
                .build();
    }

    public OrderByStatusResponseDto toOrderByStatusResponseDto(List<Order> orders) {
        OrderByStatusResponseDto orderByStatusResponseDto = new OrderByStatusResponseDto();
        OrderResponseDto orderResponseDto;

        for (Order order : orders) {
            orderResponseDto = toOrderResponseDto(order);

            if (order.isInDeliveryProcess() || order.getStatus() == OrderStatus.COMPRA_CANCELADA) {
                orderByStatusResponseDto.getDeliveredOrders().add(orderResponseDto);
                orderResponseDto.setTotalProductQuantity(order.getQuantity());

            } else if (order.isBeingTraded()) {
                orderByStatusResponseDto.getTradedOrders().add(orderResponseDto);
            }
        }

        for (OrderResponseDto tradedOrder : orderByStatusResponseDto.getTradedOrders()) {
            for (OrderResponseDto deliveredOrder : orderByStatusResponseDto.getDeliveredOrders()) {
                if (Objects.equals(deliveredOrder.getProduct().id(), tradedOrder.getProduct().id())) {
                    deliveredOrder.setTotalProductQuantity(
                            deliveredOrder.getTotalProductQuantity() + tradedOrder.getQuantity());
                }
            }
        }

        return orderByStatusResponseDto;
    }

    public Order toTradeOrder(Order order, int quantity) {
        return Order.builder()
                .transaction(order.getTransaction())
                .product(order.getProduct())
                .quantity(quantity)
                .status(OrderStatus.EM_TROCA)
                .build();
    }
}
