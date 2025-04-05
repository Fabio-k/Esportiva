package org.fatec.esportiva.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class CurrencyService {
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public String format(BigDecimal price){
        if(price == null) return numberFormat.format(BigDecimal.ZERO);
        return numberFormat.format(price);
    }
}
