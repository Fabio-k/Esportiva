package org.fatec.esportiva.service.transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.fatec.esportiva.entity.*;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.dto.request.TransactionDto;
import org.fatec.esportiva.dto.response.TransactionResponseDto;
import org.fatec.esportiva.service.AddressService;
import org.fatec.esportiva.service.ClientService;
import org.fatec.esportiva.service.ExchangeVoucherService;
import org.fatec.esportiva.service.ProductService;
import org.fatec.esportiva.service.order.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ClientService clientService;
    private final TransactionRepository transactionRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final TransactionStateFactory transactionStateFactory;
    private final AddressService addressService;

    public List<TransactionResponseDto> getTransactions() {
        return transactionRepository.findAllByClientOrderByPurchaseDateDesc(clientService.getAuthenticatedClient())
                .stream().map(TransactionMapper::toTransactionResponseDto).toList();
    }

    public List<TransactionResponseDto> getTransactions(long id) {
        return transactionRepository.findAllByClientOrderByPurchaseDateDesc(clientService.findClient(id))
                .stream().map(TransactionMapper::toTransactionResponseDto).toList();
    }

    public List<TransactionDto> getTransactionsAdmin() {
        return transactionRepository.findAll()
                .stream().map(TransactionMapper::toTransactionDto).toList();
    }

    public TransactionResponseDto getTransaction(Long id) {
        return TransactionMapper.toTransactionResponseDto(findById(id));
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("transação não encontrada"));
    }

    @Transactional
    public Transaction generateTransaction(Long addressId) {
        Address address = addressService.findById(addressId);
        Cep cep = address.getCep();
        String addressNumber = address.getNumber();
        Client client = clientService.getAuthenticatedClient();
        Transaction transaction = Transaction.builder()
                .client(client)
                .status(OrderStatus.EM_PROCESSAMENTO)
                .purchaseDate(LocalDateTime.now())
                .cep(cep)
                .addressNumber(addressNumber)
                .build();

        List<Order> orders = client.getCart().getCartItems().stream()
                .map(cartItem -> {
                    Order order = CartItemMapper.toOrder(cartItem);
                    order.setTransaction(transaction);
                    order.setStatus(OrderStatus.EM_PROCESSAMENTO);
                    return order;
                }).toList();

        transaction.setOrders(orders);

        return transactionRepository.save(transaction);
    }

    // Máquina de estados que controla a transições conforme cada aprovação
    public void changeState(long id, boolean approve) {
        Transaction transaction = findById(id);
        OrderStatus status = transaction.getStatus();
        TransactionContext context = new TransactionContext(orderService, exchangeVoucherService);

        TransactionState handler = transactionStateFactory.getHandler(status);

        if (approve)
            handler.approve(transaction, context);
        else
            handler.reprove(transaction, context);

        transactionRepository.save(transaction);
    }

    @Transactional
    public void denyTransaction(Transaction transaction) {
        List<Order> orders = transaction.getOrders();

        orders.forEach(order -> {
            productService.returnBlockedProductQuantity(order.getProduct().getId(), (short) order.getQuantity());
        });

        transaction.setStatus(OrderStatus.COMPRA_CANCELADA);
    }

    public void requestTrade(Long id) {
        Client client = clientService.getAuthenticatedClient();
        Transaction transaction = transactionRepository.findByClientAndIdAndStatus(client, id, OrderStatus.ENTREGUE)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
        List<Order> orders = new ArrayList<>(transaction.getOrders());
        for (Order order : orders) {
            orderService.tradeOrder(order.getId(), (short) order.getQuantity());
        }
    }
}