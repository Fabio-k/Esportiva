package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.request.CreditCardDto;

@UtilityClass
public class CreditCardMapper {
    public CreditCard toCreditCard(CreditCardDto creditCardDto){
        return CreditCard.builder()
                .name(creditCardDto.getName())
                .securityCode(creditCardDto.getSecurityCode())
                .number(creditCardDto.getNumber())
                .brand(creditCardDto.getBrand())
                .build();
    }

    public CreditCardDto toCreditCardDto(CreditCard creditCard){
        return CreditCardDto.builder()
                .id(creditCard.getId())
                .brand(creditCard.getBrand())
                .securityCode(creditCard.getSecurityCode())
                .name(creditCard.getName())
                .number(creditCard.getNumber())
                .build();
    }
}
