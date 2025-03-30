package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.OrderMapper;
import org.fatec.esportiva.repository.OrderRepository;
import org.fatec.esportiva.request.OrderDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> getTransactions(OrderStatus status) {
        return orderRepository.findAllByStatus(status)
                .stream().map(OrderMapper::toOrderDto).toList();
    }
}
