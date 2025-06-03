package org.fatec.esportiva.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class StringUtils {

    public String format(BigDecimal price){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        if(price == null) return numberFormat.format(BigDecimal.ZERO);
        return numberFormat.format(price);
    }

    public static String maskCreditCardNumber(String number){
        if(number.length() < 4) return "";
        return "**** " + number.substring(number.length() - 4);
    }
}
