package org.fatec.esportiva.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.request.SplitCreditCardDto;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.repository.CreditCardRepository;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.fatec.esportiva.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CurrencyService currencyService;

    public List<CreditCard> createCreditCards(Client client, List<CreditCardDto> creditCardDtos){
        return creditCardDtos.stream().map(c -> {
            CreditCard creditCard = CreditCardMapper.toCreditCard(c);
            creditCard.setClient(client);
            creditCard.setId(null);
            return creditCard;
        }).toList();
    }

    public List<CreditCard> updateOrCreateCreditCards(Client client, List<CreditCardDto> creditCardDtos){
        return creditCardDtos.stream().map(c -> {
            if(c.getId() != null){
                CreditCard creditCard = findCreditCard(c.getId());
                creditCard.setName(c.getName());
                creditCard.setNumber(c.getNumber());
                creditCard.setBrand(c.getBrand());
                creditCard.setSecurityCode(c.getSecurityCode());

                return creditCard;
            }
            CreditCard creditCard = CreditCardMapper.toCreditCard(c);
            creditCard.setClient(client);
            return creditCard;
        }).toList();
    }

    public CreditCardDto createCreditCard(Client client, CreditCardDto creditCardDto, Boolean isTemporary){
        CreditCard creditCard = CreditCardMapper.toCreditCard(creditCardDto);
        creditCard.setClient(client);
        creditCard.setId(null);
        creditCard.setIsTemporary(isTemporary);
        creditCard.setExpireAt(LocalDateTime.now());
        return CreditCardMapper.toCreditCardDto(creditCardRepository.save(creditCard));
    }

    public CreditCard findCreditCard(Long id){
        return creditCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão de crédito não encontrado"));
    }

    public List<SplitCreditCardDto> findAllByIdAndClientId(List<Long> ids, Long clientId){
        return creditCardRepository.findAllByIdInAndClientId(ids, clientId).stream()
                .map(creditCard -> CreditCardMapper.toSplitCreditCardDto(creditCard, BigDecimal.ZERO)).toList();
    }

    public List<SplitCreditCardResponseDto> getSplitCreditCardResponseDto(List<SplitCreditCardDto> splitCreditCardDtos, Long clientId){
        List<CreditCard> creditCards = creditCardRepository.findAllByIdInAndClientId(
                splitCreditCardDtos.stream().map(SplitCreditCardDto::getId).toList(),
                clientId);

        if(creditCards.size() != splitCreditCardDtos.size()) throw new IllegalArgumentException("Erro no processamento dos cartões");

        Map<Long, String> numberById = creditCards.stream()
                .collect(Collectors.toMap(CreditCard::getId, creditCard -> StringUtils.maskCreditCardNumber(creditCard.getNumber())
                ));


        return splitCreditCardDtos.stream().map(
                dto -> new SplitCreditCardResponseDto(
                        numberById.get(dto.getId()),
                        currencyService.format(dto.getValue())
                )
        ).toList();
    }
}
