package org.fatec.esportiva.response;

import java.math.BigDecimal;

public record PromotionalCouponResponseDto(String code, Integer discount, String discountValue, BigDecimal discountPrice) {
}
