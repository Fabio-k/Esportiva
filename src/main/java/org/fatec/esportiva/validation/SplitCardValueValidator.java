package org.fatec.esportiva.validation;

import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.CheckoutException;
import org.fatec.esportiva.service.CheckoutSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SplitCardValueValidator{
    @Autowired
    private CheckoutSessionService checkoutSessionService;
    public void validate(CheckoutValidationContext context, List<SplitCreditCardDto> splitCreditCardDtos) {
        CheckoutSession checkoutSession = context.getCheckoutSession();
        BigDecimal splitPaymentTotal = splitCreditCardDtos.stream().map(SplitCreditCardDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        splitCreditCardDtos.forEach(payment -> {
            if(payment.getValue().compareTo(BigDecimal.TEN) < 0) throw new CheckoutException("Cada cartão deve pagar pelo menos R$ 10,00.", context.getRedirectPage());
        });
        BigDecimal result = checkoutSessionService.calculateTotalPrice(checkoutSession).subtract(splitPaymentTotal);
        if(result.compareTo(BigDecimal.ZERO) < 0) throw new CheckoutException("Valor escolhido supera o valor a ser pago", context.getRedirectPage());
        if(result.compareTo(BigDecimal.ZERO) > 0) throw new CheckoutException("Valor escolhido é insuficiente", context.getRedirectPage());
    }
}
