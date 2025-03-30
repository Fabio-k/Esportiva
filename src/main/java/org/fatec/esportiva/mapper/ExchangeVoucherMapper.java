package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.request.ExchangeVoucherDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExchangeVoucherMapper {
    public ExchangeVoucher toExchangeVoucher(Client client, ExchangeVoucherDto exchangeVoucherDto) {
        return ExchangeVoucher.builder()
                .value(exchangeVoucherDto.getValue())
                .quantity(exchangeVoucherDto.getQuantity())
                .client(client)
                .build();
    }

    public ExchangeVoucherDto toExchangeVoucherDto(ExchangeVoucher exchangeVoucher) {
        return ExchangeVoucherDto.builder()
                .id(exchangeVoucher.getId())
                .value(exchangeVoucher.getValue())
                .quantity(exchangeVoucher.getQuantity())
                .client(exchangeVoucher.getClient())
                .build();
    }
}
