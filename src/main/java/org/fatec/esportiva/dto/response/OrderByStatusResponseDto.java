package org.fatec.esportiva.dto.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderByStatusResponseDto {
    List<OrderResponseDto> deliveredOrders = new ArrayList<>(); // Todos os pedidos até a entrega do cliente (Compra)
    List<OrderResponseDto> tradedOrders = new ArrayList<>(); // Todos os pedidos em devolução
}
