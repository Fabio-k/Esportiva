package org.fatec.esportiva.service.order;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.OrderMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.repository.OrderRepository;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.dto.request.OrderDto;
import org.fatec.esportiva.dto.response.OrderResponseDto;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.service.ClientService;
import org.fatec.esportiva.service.ExchangeVoucherService;
import org.fatec.esportiva.service.NotificationService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final NotificationService notificationService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final TransactionRepository transactionRepository;
    private final OrderItemHandlerFactory orderItemHandlerFactory;

    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findAllByStatus(status)
                .stream().map(OrderMapper::toOrderDto).toList();
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(OrderMapper::toOrderDto).toList();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    }

    public Order findOrderByClientIdAndId(Long id) {
        Client client = clientService.getAuthenticatedClient();
        return orderRepository.findByTransactionClientIdAndId(client.getId(), id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
    }

    public OrderResponseDto findByClientIdAndId(Long id) {
        Order order = findOrderByClientIdAndId(id);
        return OrderMapper.toOrderResponseDto(order);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve, boolean stock) {
        Order order = findById(id);
        Product product = order.getProduct();
        Client client = order.getTransaction().getClient();
        OrderStatus status = order.getStatus();
        OrderItemHandlerContext context = new OrderItemHandlerContext(stock, notificationService,
                exchangeVoucherService);

        OrderState handler = orderItemHandlerFactory.getHandler(status);
        if (approve) {
            handler.approve(order, context);
        } else {
            handler.reprove(order, context);
        }

        orderRepository.save(order);
        productRepository.save(product);
        clientRepository.save(client);
    }

    @Transactional
    public void tradeOrder(Long id, Short quantity) {
        Order order = findOrderByClientIdAndId(id);

        validateOrder(order, quantity);

        processTradeOrder(order, quantity);
    }

    private void validateOrder(Order order, int quantity) {
        if (order.hasInsufficientQuantity(quantity))
            throw new IllegalArgumentException("Quantidade inválida");

        if (order.getStatus() != OrderStatus.ENTREGUE)
            throw new IllegalArgumentException("pedido não está com status entregue");
    }

    private void processTradeOrder(Order order, int quantity) {
        order.decreaseQuantity(quantity);
        orderRepository.save(order);

        Order newOrder = OrderMapper.toTradeOrder(order, quantity);
        orderRepository.save(newOrder);

        // Atualizar a transação, incluindo o novo pedido na coleção de orders
        Transaction transaction = order.getTransaction(); // Obtém a transação do pedido existente
        transaction.addOrder(newOrder); // Adiciona o novo pedido à lista de orders

        // Salvar a transação para garantir que o relacionamento seja persistido
        transactionRepository.save(transaction);
    }
}
