package org.fatec.esportiva.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.OrderMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.repository.OrderRepository;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.request.OrderDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public List<OrderDto> getTransactions(OrderStatus status) {
        return orderRepository.findAllByStatus(status)
                .stream().map(OrderMapper::toOrderDto).toList();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) throws Exception {
        Order order = getNonOptional(orderRepository.findById(id));
        OrderStatus status = order.getStatus();

        // Máquina de estados
        if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                order.setStatus(OrderStatus.EM_TROCA);
                // Aparece um aviso para o cliente, pode ser uma lista de produtos que estão
                // nesse estado
            } else {
                // Volta para entregue e recusa o pedido???
            }
        } else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                order.setStatus(OrderStatus.TROCADO);
            } else {
                // Volta para entregue e recusa o pedido???
            }
        } else if (status == OrderStatus.TROCADO) {
            order.setStatus(OrderStatus.TROCA_FINALIZADA);

            // O produto volta para o estoque
            // Gera cupom para o cliente naquele valor
        } else {
            // Não faz nada nos outros estados que são da transação
        }

        orderRepository.save(order);
    }

    // Remove o "Optional" do tipo que o linter reclama
    private static <T> T getNonOptional(Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("Optional está vazio.");
        }
    }
}
