package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.*;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.repository.TransactionsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final ClientService clientService;
    private final AddressService addressService;
    private final CreditCardService creditCardService;
    private final TransactionsRepository transactionsRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Transactional
    public void generateTransaction(CheckoutSession checkoutSession){
        Address address = addressService.findById(checkoutSession.getAddressId());
        List<CreditCard> creditCards = checkoutSession.getCreditCardIds().stream().map(creditCardService::findCreditCard).toList();

        Client client = clientService.getAuthenticatedClient();
        Transactions transaction = Transactions.builder()
                .client(client)
                .purchaseDate(LocalDate.now())
                .build();


        List<Order> orders = client.getCart().getCartItems().stream()
                .map(cartItem -> {
                    Order order = CartItemMapper.toOrder(cartItem);
                    order.setTransaction(transaction);
                    return order;
                }).toList();

        transaction.setOrders(orders);

        transactionsRepository.save(transaction);
        productService.updateQuantityAfterPurchase(orders);
        cartService.cleanCart();
    }
}
