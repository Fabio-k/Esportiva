package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.TransactionMapper;
import org.fatec.esportiva.repository.TransactionRepository;
import org.fatec.esportiva.response.TransactionResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;
    private final TransactionRepository transactionRepository;
    private final ProductService productService;
    private final CartService cartService;

    public List<TransactionResponseDto> getTransactions(){
        Client client = clientService.getAuthenticatedClient();
        List<Transaction> transactions = transactionRepository.findAllByClientOrderByPurchaseDateDesc(client);
        return transactions.stream().map(TransactionMapper::toDto).toList();
    }

    @Transactional
    public void generateTransaction(CheckoutSession checkoutSession){
        Address address = addressService.findById(checkoutSession.getAddressId());
        List<CreditCard> creditCards = checkoutSession.getCreditCardIds().stream().map(creditCardService::findCreditCard).toList();

        Client client = clientService.getAuthenticatedClient();
        Transaction transaction = Transaction.builder()
                .client(client)
                .status(OrderStatus.EM_PROCESSAMENTO)
                .purchaseDate(LocalDate.now())
                .build();


        List<Order> orders = client.getCart().getCartItems().stream()
                .map(cartItem -> {
                    Order order = CartItemMapper.toOrder(cartItem);
                    order.setTransaction(transaction);
                    order.setStatus(OrderStatus.EM_PROCESSAMENTO);
                    return order;
                }).toList();

        transaction.setOrders(orders);

        productService.updateQuantityAfterPurchase(orders);
        cartService.cleanCart();
        transactionRepository.save(transaction);
    }
}
