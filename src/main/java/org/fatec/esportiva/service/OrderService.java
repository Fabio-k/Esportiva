package org.fatec.esportiva.service;

import java.util.List;
import java.util.Optional;

import org.fatec.esportiva.entity.Order;
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

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(id);
        OrderStatus status;
        Order order;

        // Pega o item da tabela
        if (orderOptional.isPresent()) {
            order = orderOptional.get();
            status = order.getStatus();
        } else {
            throw new Exception("Não foi possível achar a transação: " + id);
        }

        // Máquina de estados
        if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                order.setStatus(OrderStatus.EM_TROCA);
                // Aparece um aviso para o cliente, pode ser uma lista de produtos que estão
                // nesse estado
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
            }
        } else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                order.setStatus(OrderStatus.TROCADO);
            } else {
                // Deletar a transação e reembolsar com cupom de dinheiro
                // O produto volta para o estoque
            }
        } else if (status == OrderStatus.TROCADO) {
            order.setStatus(OrderStatus.TROCA_FINALIZADA);
        } else {
            // Não faz nada nos outros estados que são da transação
        }

        orderRepository.save(order);
    }
}
