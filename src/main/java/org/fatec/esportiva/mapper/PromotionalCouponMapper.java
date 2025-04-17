package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.PromotionalCoupon;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;

import java.math.BigDecimal;

@UtilityClass
public class PromotionalCouponMapper {
    public PromotionalCouponResponseDto toPromotionalCouponResponseDto(PromotionalCoupon promotionalCoupon, String discountValue, BigDecimal discountPrice){
        return new PromotionalCouponResponseDto(promotionalCoupon.getCode(), promotionalCoupon.getDiscount(), discountValue, discountPrice);
    }
}
