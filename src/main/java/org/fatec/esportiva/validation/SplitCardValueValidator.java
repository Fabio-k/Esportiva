package org.fatec.esportiva.validation;

import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.ApiException;
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
            if (isLesThanTenAndNoVoucherApplied(payment, checkoutSession))
                throw new ApiException("Cada cartão deve pagar pelo menos R$ 10,00.");
            if(payment.getValue().compareTo(BigDecimal.ZERO) <= 0 ) throw new ApiException("Cartões devem pager valor acima de R$ 0,00");
        });

        BigDecimal result = checkoutSessionService.calculateTotalPrice(checkoutSession).subtract(splitPaymentTotal);
        if(result.compareTo(BigDecimal.ZERO) < 0) throw new ApiException("Valor escolhido supera o valor a ser pago");
        if(result.compareTo(BigDecimal.ZERO) > 0) throw new ApiException("Valor escolhido é insuficiente");
    }

    private Boolean isLesThanTenAndNoVoucherApplied(SplitCreditCardDto payment, CheckoutSession checkoutSession){
        BigDecimal totalDiscount = checkoutSessionService.getTotalDiscount(checkoutSession);
        return payment.getValue().compareTo(BigDecimal.TEN) < 0 && totalDiscount.compareTo(BigDecimal.ZERO) == 0;
    }
}
