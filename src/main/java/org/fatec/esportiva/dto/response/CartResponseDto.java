package org.fatec.esportiva.dto.response;

import org.fatec.esportiva.service.CurrencyService;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDto(List<CartItemResponseDto> items, CurrencyService currencyService, List<ProductResponseDto> removedProducts) {

    public String getTotalPrice() {
        BigDecimal total = items.stream()
                .map(CartItemResponseDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return currencyService.format(total);
    }
}
