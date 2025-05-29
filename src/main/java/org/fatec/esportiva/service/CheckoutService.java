package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.service.cart.CartService;
import org.fatec.esportiva.service.transaction.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final CheckoutSessionService checkoutSessionService;

    @Transactional
    public void processCheckout(CheckoutSession checkoutSession, Model model){
        Transaction transaction = transactionService.generateTransaction();
        try{
            checkoutSessionService.validatePayment(checkoutSession);
            exchangeVoucherService.validateExchangeVoucherOwnership(checkoutSession.getExchangeVoucherIds(), clientService.getAuthenticatedClient().getId());
        } catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            transactionService.denyTransaction(transaction);
        }

        exchangeVoucherService.markAsUsedExchangeVouchers(checkoutSession.getExchangeVoucherIds(), clientService.getAuthenticatedClient().getId());
        checkoutSessionService.generateExchangeVoucher(checkoutSession);
        cartService.cleanCart();
        checkoutSessionService.clearCheckoutSession(checkoutSession);
    }
}
