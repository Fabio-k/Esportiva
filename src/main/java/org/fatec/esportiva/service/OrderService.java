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
import org.fatec.esportiva.request.OrderDto;
import org.fatec.esportiva.response.OrderResponseDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;

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
    public BigDecimal changeState(long id, boolean approve) {
        BigDecimal voucherValue = ZERO; // Por padrão, o valor que nada aconteceu é zero
        Order order = getNonOptional(orderRepository.findById(id));
        Product product = order.getProduct();
        Client client = order.getTransaction().getClient();
        OrderStatus status = order.getStatus();

        // Máquina de estados
        if  (status == OrderStatus.EM_PROCESSAMENTO) {
            if (approve == true) {
                // Dá a baixa no estoque aqui e desbloqueia os produtos
                order.setStatus(OrderStatus.EM_TRANSITO);
                product.decreaseStock(order.getQuantity());
            } else {
                // Reembolsa o cliente
                order.setStatus(OrderStatus.COMPRA_CANCELADA);
                voucherValue = calculateVoucherValue(order);
            }
        }

        else if (status == OrderStatus.EM_TRANSITO) {
            if (approve == true) {
                // Vai para a casa do cliente
                order.setStatus(OrderStatus.ENTREGUE);
            } else {
                // Produto que estava indo para o cliente, volta para o estoque antes de chegar
                // na sua casa
                // Reembolsa o cliente
                order.setStatus(OrderStatus.COMPRA_CANCELADA);
                product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
                voucherValue = calculateVoucherValue(order);
            }
        }

        else if (status == OrderStatus.ENTREGUE) {
            if (approve == true) {
                // Aparece um aviso para o cliente que a troca foi aceita
                // Pode ser uma lista de produtos que estão nesse estado
                order.setStatus(OrderStatus.EM_TROCA);
            } else {
                // Não faz nada, porque o cliente só pode solicitar devolução ou fazer nada
            }

        }

        else if (status == OrderStatus.EM_TROCA) {
            if (approve == true) {
                // Troca aceita
                order.setStatus(OrderStatus.TROCADO);
            } else {
                // Troca recusada, não faz nada
                order.setStatus(OrderStatus.TROCA_RECUSADA);
            }

        }

        else if (status == OrderStatus.TROCADO) {
            if (approve == true) {
                // Repõe o estoque quando a troca é finalizada
                // Reembolsa o cliente
                order.setStatus(OrderStatus.TROCA_FINALIZADA);
                product.setStockQuantity(product.getStockQuantity() + order.getQuantity());
                voucherValue = calculateVoucherValue(order);
                client = refundVoucher(client, voucherValue);
            } else {
                // Produto não chegou na loja, troca recusada novamente
                order.setStatus(OrderStatus.TROCA_RECUSADA);
            }
        }

        else {
            // Não faz nada nos outros estados
        }

        orderRepository.save(order);
        productRepository.save(product);
        clientRepository.save(client);
        return voucherValue; // Caso tenha vários produtos, soma tudo e gera um cupom somente
    }

    // Cálculo do valor do cupom (Valor gasto com esse produto)
    private BigDecimal calculateVoucherValue(Order order) {
        // Margem de lucro em porcentagem
        BigDecimal voucherValue = order.getProduct().getProfitMargin();
        // Margem de lucro em decimal = (Margem de lucro em porcentagem / 100) + 1
        voucherValue = voucherValue.divide(HUNDRED).add(ONE);
        // Preço do produto = Margem de lucro * Custo do produto
        voucherValue = voucherValue.multiply(order.getProduct().getCostValue());
        // Valor do cupom = Preço do produto * Quantidade do produto
        voucherValue = voucherValue.multiply(BigDecimal.valueOf(order.getQuantity()));

        return voucherValue;
    }

    // Gera cupom para o cliente, reembolsando o produto
    private Client refundVoucher(Client client, BigDecimal voucherValue) {
        // Cria um novo cupom
        ExchangeVoucher exchangeVoucher = new ExchangeVoucher();
        exchangeVoucher.setId(null);
        exchangeVoucher.setValue(voucherValue);
        exchangeVoucher.setClient(client);

        // Adiciona o novo cupom a lista de cupons que o cliente já tinha
        List<ExchangeVoucher> exchangeVouchers = client.getExchangeVouchers();
        exchangeVouchers.add(exchangeVoucher);
        client.setExchangeVouchers(exchangeVouchers);

        return client;
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
        if (order.hasEnoughDeliveredQuantity(quantity)) throw new IllegalArgumentException("Quantidade inválida");
        order.setQuantity(order.getQuantity() - quantity);
        orderRepository.save(order);
        Order newOrder = Order.builder()
                .transaction(order.getTransaction())
                .product(order.getProduct())
                .quantity(quantity)
                .status(order.getStatus())
                .build();

        newOrder = orderRepository.save(newOrder);
        changeState(newOrder.getId(), true);
    }
}
