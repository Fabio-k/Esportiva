package org.fatec.esportiva.validation;

import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.exception.CheckoutException;
import org.fatec.esportiva.service.CheckoutSessionService;
import org.fatec.esportiva.service.ExchangeVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class UnusedExchangeVouchersValidator implements CheckoutValidator{
    @Autowired
    private CheckoutSessionService checkoutSessionService;

    @Autowired
    private ExchangeVoucherService exchangeVoucherService;

    @Override
    public void validate(CheckoutValidationContext context) {
        CheckoutSession checkoutSession = context.getCheckoutSession();
        List<ExchangeVoucher> vouchers = exchangeVoucherService.findAllById(checkoutSession.getExchangeVoucherIds());
        if (vouchers.isEmpty()) return;

        BigDecimal freight = checkoutSessionService.getFreight(checkoutSession.getAddress());

        BigDecimal priceWithoutExchangeVouchers = checkoutSessionService.getCartTotalPrice().add(freight).subtract(checkoutSessionService.getPromotionalCouponDiscount(checkoutSession.getPromotionalCouponCode()));
        if (checkoutSessionService.getExchangeVouchersTotalPrice(checkoutSession.getExchangeVoucherIds()).compareTo(priceWithoutExchangeVouchers) < 1) return;

        vouchers.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        BigDecimal accumulated = BigDecimal.ZERO;
        int usedVouchers = 0;
        for(ExchangeVoucher exchangeVoucher : vouchers){
            accumulated = accumulated.add(exchangeVoucher.getValue());
            usedVouchers++;
            if(accumulated.compareTo(priceWithoutExchangeVouchers) >= 0) break;
        }

        if(usedVouchers < vouchers.size())
            throw new CheckoutException("Você selecionou cupons desnecessários. Remova os cupons extras.", context.getRedirectPage());
    }
}
