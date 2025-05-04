package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.dto.response.CartItemResponseDto;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.mapper.CartItemMapper;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.validation.CartEmptyValidator;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final CheckoutSessionService checkoutSessionService;

    public void processCheckout(CheckoutSession checkoutSession, Model model){
        AddressResponseDto address = checkoutSession.getAddress();
        List<CartItemResponseDto> items = clientService.getCart().items();

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

        model.addAttribute("items", items);
        model.addAttribute("address", address);
    }
}
