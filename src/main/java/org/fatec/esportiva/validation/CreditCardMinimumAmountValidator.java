package org.fatec.esportiva.validation;

import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.ApiException;
import org.fatec.esportiva.exception.CheckoutException;
import org.fatec.esportiva.service.CheckoutSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CreditCardMinimumAmountValidator implements CheckoutValidator{
    @Autowired
    private CheckoutSessionService checkoutSessionService;

    @Override
    public void validate(CheckoutValidationContext context) {
        CheckoutSession checkoutSession = context.getCheckoutSession();
        if(checkoutSessionService.getTotalDiscount(checkoutSession).compareTo(BigDecimal.ZERO) > 0) return;

        int numberOfCreditCards = checkoutSessionService.getCreditCardsDto(checkoutSession).size();
        if (numberOfCreditCards == 0) return;

        BigDecimal totalPrice = checkoutSessionService.calculateTotalPrice(checkoutSession);
        if (totalPrice.divide(BigDecimal.valueOf(numberOfCreditCards), RoundingMode.UP).compareTo(BigDecimal.TEN) < 0)
            throw new ApiException("Cada cartão deve pagar pelo menos R$ 10,00.");
    }
}
