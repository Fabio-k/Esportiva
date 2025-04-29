package org.fatec.esportiva.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public static String maskCreditCardNumber(String number){
        if(number.length() < 4) return "";
        return "**** " + number.substring(number.length() - 4);
    }
}
