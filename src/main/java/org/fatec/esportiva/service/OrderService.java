package org.fatec.esportiva.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Product;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.OrderMapper;
import org.fatec.esportiva.repository.ClientRepository;
import org.fatec.esportiva.repository.OrderRepository;
import org.fatec.esportiva.repository.ProductRepository;
import org.fatec.esportiva.dto.request.OrderDto;
import org.fatec.esportiva.dto.response.OrderResponseDto;
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

    // Constantes para aumentar a legibilidade
    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public List<OrderDto> getTransactions(OrderStatus status) {
        return orderRepository.findAllByStatus(status)
                .stream().map(OrderMapper::toOrderDto).toList();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderResponseDto findByClientIdAndId(Long id){
        Client client = clientService.getAuthenticatedClient();
        Order order = orderRepository.findByTransactionClientIdAndId(client.getId(), id).orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        return OrderMapper.toOrderResponseDto(order);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) {
        Order order = getNonOptional(orderRepository.findById(id));
        Product product = order.getProduct();
        Client client = order.getTransaction().getClient();
        OrderStatus status = order.getStatus();

        // Máquina de estados
        if  (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve) {
                // Dá a baixa no estoque aqui e desbloqueia os produtos
                order.setStatus(OrderStatus.EM_TRANSITO);
                product.decreaseStock(order.getQuantity());
            } else {
                // Reembolsa o cliente
                order.setStatus(OrderStatus.COMPRA_CANCELADA);
            }
        }

        else if (status == OrderStatus.EM_TRANSITO) {
            if (approve) {
                // Vai para a casa do cliente
                order.setStatus(OrderStatus.ENTREGUE);
            } else {
                // Produto que estava indo para o cliente, volta para o estoque antes de chegar
                // na sua casa
                // Reembolsa o cliente
                order.setStatus(OrderStatus.COMPRA_CANCELADA);
                product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
            }
        }

        else if (status == OrderStatus.ENTREGUE) {
            if (approve) {
                // Aparece um aviso para o cliente que a troca foi aceita
                // Pode ser uma lista de produtos que estão nesse estado
                order.setStatus(OrderStatus.EM_TROCA);
            } else {
                // Não faz nada, porque o cliente só pode solicitar devolução ou fazer nada
            }

        }

        else if (status == OrderStatus.EM_TROCA) {
            if (approve) {
                // Troca aceita
                notificationService.notifyTradeAccepted(order);
                order.setStatus(OrderStatus.TROCADO);
                exchangeVoucherService.createExchangeVoucher(client, order.getTotalPrice());
            } else {
                // Troca recusada, não faz nada
                order.setStatus(OrderStatus.TROCA_RECUSADA);
            }

        }

        else if (status == OrderStatus.TROCADO) {
            if (approve) {
                // Repõe o estoque quando a troca é finalizada
                // Reembolsa o cliente
                order.setStatus(OrderStatus.TROCA_FINALIZADA);
                product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
            } else {
                // Produto não chegou na loja, troca recusada novamente
                order.setStatus(OrderStatus.TROCA_RECUSADA);
            }
        }

        orderRepository.save(order);
        productRepository.save(product);
        clientRepository.save(client);// Caso tenha vários produtos, soma tudo e gera um cupom somente
    }

    // Remove o "Optional" do tipo que o linter reclama
    private static <T> T getNonOptional(Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchElementException("Optional está vazio.");
        }
    }

    @Transactional
    public void tradeOrder(Long id, Short quantity) {
        Client client = clientService.getAuthenticatedClient();
        Order order = orderRepository.findByTransactionClientIdAndId(client.getId(), id).orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        if (order.hasInsufficientQuantity(quantity) || order.getStatus() != OrderStatus.ENTREGUE) throw new IllegalArgumentException("Quantidade inválida");
        order.setQuantity(order.getQuantity() - quantity);
        orderRepository.save(order);
        Order newOrder = Order.builder()
                .transaction(order.getTransaction())
                .product(order.getProduct())
                .quantity(quantity)
                .status(OrderStatus.ENTREGUE)
                .build();

        newOrder = orderRepository.save(newOrder);
        changeState(newOrder.getId(), true);
    }
}
