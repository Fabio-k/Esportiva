package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.mapper.ExchangeVoucherMapper;
import org.fatec.esportiva.repository.ExchangeVoucherRepository;
import org.fatec.esportiva.request.ExchangeVoucherDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeVoucherService {
    private final ExchangeVoucherRepository exchangeVoucherRepository;

    public void validateExchangeVoucherOwnership(List<Long> voucherIds, Long clientId){
        List<ExchangeVoucher> vouchers = exchangeVoucherRepository.findAllByIdInAndClientId(voucherIds, clientId);
        if(voucherIds.size() != vouchers.size()) throw new IllegalArgumentException("Um ou mais vouchers não foram encontrados");
    }

    public List<ExchangeVoucher> updateOrCreateExchangeVoucher(Client client,
            List<ExchangeVoucherDto> exchangeVoucherDto) {
        return exchangeVoucherDto.stream().map(c -> {
            if (c.getId() != null) {
                ExchangeVoucher exchangeVoucher = exchangeVoucherRepository.findById(c.getId())
                        .orElseThrow(() -> new RuntimeException("Cupom de desconto não encontrado"));
                exchangeVoucher.setValue(c.getValue());

                return exchangeVoucher;
            }

            return ExchangeVoucherMapper.toExchangeVoucher(client, c);
        }).toList();
    }

    public List<ExchangeVoucher> findAllById(List<Long> ids){
        return exchangeVoucherRepository.findAllById(ids);
    }
}
