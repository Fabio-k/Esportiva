package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.entity.session.CheckoutSession;
import org.fatec.esportiva.response.PromotionalCouponResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final ExchangeVoucherService exchangeVoucherService;
    private final PromotionalCouponService promotionalCouponService;

    public BigDecimal calculateTotalPrice(CheckoutSession checkoutSession){
        BigDecimal totalDiscount = BigDecimal.ZERO;
        if(checkoutSession.getExchangeVoucherIds() != null){
            totalDiscount = getTotalExchangeVoucherDiscount(checkoutSession);
        }

        return getCartTotalPrice().subtract(totalDiscount).max(BigDecimal.ZERO);
    }

    public BigDecimal getCartTotalPrice(){
        return cartService.getTotalCartPrice();
    }

    public BigDecimal getTotalExchangeVoucherDiscount(CheckoutSession checkoutSession){
        List <ExchangeVoucher> vouchers = exchangeVoucherService.findAllById(checkoutSession.getExchangeVoucherIds());
        BigDecimal promotionalCouponDiscount = BigDecimal.ZERO;
        PromotionalCouponResponseDto responseDto = promotionalCouponService.getPromotionalCouponOrReturnNull(checkoutSession.getPromotionalCouponCode());
        if(responseDto != null)  {
            promotionalCouponDiscount = responseDto.discountPrice();
        }
        return vouchers.stream()
                .map(ExchangeVoucher::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add).add(promotionalCouponDiscount);
    }
}
