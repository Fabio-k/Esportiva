package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.utils.StringUtils;

import java.math.BigDecimal;

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

    public SplitCreditCardDto toSplitCreditCardDto(CreditCard creditCard, BigDecimal value){
        return new SplitCreditCardDto(creditCard.getId(), StringUtils.maskCreditCardNumber(creditCard.getNumber()), value);
    }

    public SplitCreditCardResponseDto toSplitCreditCardResponseDto(Long id, String number, BigDecimal value){
        return new SplitCreditCardResponseDto(id, StringUtils.maskCreditCardNumber(number), StringUtils.format(value));
    }
}
