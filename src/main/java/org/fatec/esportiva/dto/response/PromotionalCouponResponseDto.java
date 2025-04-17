package org.fatec.esportiva.dto.response;

import java.math.BigDecimal;

public record PromotionalCouponResponseDto(String code, Integer discount, String discountValue, BigDecimal discountPrice) {
}
