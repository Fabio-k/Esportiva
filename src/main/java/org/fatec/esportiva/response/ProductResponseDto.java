package org.fatec.esportiva.response;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public record ProductResponseDto(Long id, int quantity, String name, BigDecimal price, String description, String image) {
    @SuppressWarnings("deprecation")
    public String getFormattedPrice(){
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(price);
    }
}
