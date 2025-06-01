package org.fatec.esportiva.validation;

import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.CheckoutException;
import org.fatec.esportiva.service.CheckoutSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NoNeedForCreditCardPaymentValidator implements CheckoutValidator{
    @Autowired
    private CheckoutSessionService checkoutSessionService;

    @Override
    public void validate(CheckoutValidationContext context) {
        CheckoutSession checkoutSession = context.getCheckoutSession();
        if (checkoutSessionService.getCreditCardsDto(checkoutSession).isEmpty()) return;

        if(checkoutSessionService.calculateTotalPrice(checkoutSession).compareTo(BigDecimal.ZERO) <= 0)
            throw new CheckoutException("O pedido já está sendo totalmente pago através de descontos. Remova os cartões de crédito para continuar.", context.getRedirectPage());
    }
}
