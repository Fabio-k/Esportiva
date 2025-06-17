package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.PromotionalCoupon;
import org.fatec.esportiva.exception.ApiException;
import org.fatec.esportiva.mapper.PromotionalCouponMapper;
import org.fatec.esportiva.repository.PromotionalCouponRepository;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.service.cart.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionalCouponService {
    private final PromotionalCouponRepository promotionalCouponRepository;
    private final CartService cartService;
    private final CurrencyService currencyService;

    public PromotionalCouponResponseDto getPromotionalCoupon(String code){
        PromotionalCoupon promotionalCoupon = promotionalCouponRepository.findByCode(code).orElseThrow(() -> new ApiException("Cupom não encontrado"));
        BigDecimal discount = BigDecimal.valueOf(promotionalCoupon.getDiscount() / 100.0);
        BigDecimal totalPromotionDiscount = cartService.getTotalCartPrice().multiply(discount);
        return PromotionalCouponMapper.toPromotionalCouponResponseDto(promotionalCoupon, currencyService.format(totalPromotionDiscount), totalPromotionDiscount);
    }

    public PromotionalCouponResponseDto getPromotionalCouponOrReturnNull(String code){
        Optional<PromotionalCoupon> promotionalCoupon = promotionalCouponRepository.findByCode(code);
        if(promotionalCoupon.isEmpty()) return null;
        BigDecimal discount = BigDecimal.valueOf(promotionalCoupon.get().getDiscount() / 100.0);
        BigDecimal totalPromotionDiscount = cartService.getTotalCartPrice().multiply(discount);
        return PromotionalCouponMapper.toPromotionalCouponResponseDto(promotionalCoupon.get(), currencyService.format(totalPromotionDiscount), totalPromotionDiscount);
    }
}
