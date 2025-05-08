package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.validation.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final CartEmptyValidator cartEmptyValidator;
    private final ExchangeVoucherService exchangeVoucherService;
    private final PromotionalCouponService promotionalCouponService;
    private final ClientService clientService;
    private final CheckoutSessionService checkoutService;
    private final CreditCardService creditCardService;
    private final CreditCardMinimumAmountValidator creditCardMinimumAmountValidator;
    private final UnusedExchangeVouchersValidator unusedExchangeVouchersValidator;
    private final SplitCardValueValidator splitCardValueValidator;
    private final CreditCardPaymentValidator creditCardPaymentValidator;


    public void savePaymentMethods(CheckoutSession checkoutSession, List<Long> exchangeVoucherIds, List<Long> creditCardsIds, String promotionalCouponCode){
        cartEmptyValidator.validate();

        checkoutSession.getExchangeVoucherIds().clear();
        if(exchangeVoucherIds != null) {
            exchangeVoucherService.validateExchangeVoucherOwnership(exchangeVoucherIds, clientService.getAuthenticatedClient().getId());
            checkoutSession.setExchangeVoucherIds(exchangeVoucherIds);
        }
        PromotionalCouponResponseDto promotionalCoupon = promotionalCouponService.getPromotionalCouponOrReturnNull(promotionalCouponCode);
        if(promotionalCoupon != null) {
            checkoutSession.setPromotionalCouponCode(promotionalCoupon.code());
        }

        checkoutSession.getCreditCardIds().clear();
        if(creditCardsIds != null)
            checkoutSession.setCreditCardIds(creditCardsIds);

        CheckoutValidationContext context = new CheckoutValidationContext(checkoutSession, null, "/checkout/billing");
        creditCardPaymentValidator.validate(context);
        creditCardMinimumAmountValidator.validate(context);
        unusedExchangeVouchersValidator.validate(context);
        checkoutService.validateInsufficientPayment(checkoutSession);
    }

    public void saveSplitCardAmount(CheckoutSession checkoutSession, List<SplitCreditCardDto> splitPayment){
        checkoutSession.getCreditCardPayments().clear();
        CheckoutValidationContext context = new CheckoutValidationContext(checkoutSession, null, "/checkout/billing/split-cards");
        creditCardMinimumAmountValidator.validate(context);
        splitCardValueValidator.validate(context, splitPayment);
        List<SplitCreditCardResponseDto> splitCreditCardResponseDtos = creditCardService.getSplitCreditCardResponseDto(splitPayment, clientService.getAuthenticatedClient().getId());
        checkoutSession.getCreditCardPayments().addAll(splitCreditCardResponseDtos);
    }
}
