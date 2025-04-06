package org.fatec.esportiva.response;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public record CartItemResponseDto(Long id, Short quantity, ProductResponseDto product) {
    public BigDecimal getTotalPrice(){
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }

    public String getFormattedTotalPrice(){
        @SuppressWarnings("deprecation")
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(getTotalPrice());
    }
}
