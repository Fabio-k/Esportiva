package org.fatec.esportiva.service;

import java.math.BigDecimal;
import java.util.List;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.ExchangeVoucher;
import org.fatec.esportiva.mapper.ExchangeVoucherMapper;
import org.fatec.esportiva.repository.ExchangeVoucherRepository;
import org.fatec.esportiva.dto.request.ExchangeVoucherDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeVoucherService {
    private final ExchangeVoucherRepository exchangeVoucherRepository;

    public void createExchangeVoucher(Client client, BigDecimal value){
        ExchangeVoucher exchangeVoucher = new ExchangeVoucher();
        exchangeVoucher.setId(null);
        exchangeVoucher.setValue(value);
        exchangeVoucher.setClient(client);
        exchangeVoucherRepository.save(exchangeVoucher);
    }

    public void validateExchangeVoucherOwnership(List<Long> voucherIds, Long clientId){
        List<ExchangeVoucher> vouchers = exchangeVoucherRepository.findAllByIdInAndClientIdAndIsUsedFalse(voucherIds, clientId);
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
    public List<ExchangeVoucher> findAllValidExchangeVouchersByClientId(Long clientId){return exchangeVoucherRepository.findAllByClientIdAndIsUsedFalse(clientId);}

    public void markAsUsedExchangeVouchers(List<Long> ids, Long clientId){
        List<ExchangeVoucher> vouchers = exchangeVoucherRepository.findAllByIdInAndClientIdAndIsUsedFalse(ids, clientId);
        for(ExchangeVoucher exchangeVoucher : vouchers){
            exchangeVoucher.setIsUsed(true);
        }
        exchangeVoucherRepository.saveAll(vouchers);
    }

}
