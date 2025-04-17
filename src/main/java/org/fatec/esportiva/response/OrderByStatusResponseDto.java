package org.fatec.esportiva.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderByStatusResponseDto {
    List<OrderResponseDto> deliveredOrders = new ArrayList<>();
    List<OrderResponseDto> tradedOrders = new ArrayList<>();
}
