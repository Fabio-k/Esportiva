package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.dto.request.ExchangeVoucherDto;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.dto.response.ExchangeVoucherResponseDto;

@UtilityClass
public class ExchangeVoucherMapper {
    public ExchangeVoucher toExchangeVoucher(Client client, ExchangeVoucherDto exchangeVoucherDto) {
        return ExchangeVoucher.builder()
                .value(exchangeVoucherDto.getValue())
                .client(client)
                .build();
    }

    public ExchangeVoucherDto toExchangeVoucherDto(ExchangeVoucher exchangeVoucher) {
        return ExchangeVoucherDto.builder()
                .id(exchangeVoucher.getId())
                .value(exchangeVoucher.getValue())
                .client(exchangeVoucher.getClient())
                .build();
    }

    public ExchangeVoucherResponseDto toExchangeVoucherResponseDto(ExchangeVoucher exchangeVoucher, String formattedValue) {
        return ExchangeVoucherResponseDto.builder()
                .id(exchangeVoucher.getId())
                .value(exchangeVoucher.getValue())
                .formattedValue(formattedValue)
                .build();
    }
}
