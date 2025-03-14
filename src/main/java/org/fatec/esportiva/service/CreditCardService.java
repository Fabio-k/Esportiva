package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.repository.CreditCardRepository;
import org.fatec.esportiva.request.CreditCardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public List<CreditCard> createCreditCards(Client client, List<CreditCardDto> creditCardDtos){
        return creditCardDtos.stream().map(c -> {
            CreditCard creditCard = CreditCardMapper.toCreditCard(client, c);
            creditCard.setId(null);
            return creditCard;
        }).toList();
    }

    public List<CreditCard> updateOrCreateCreditCards(Client client, List<CreditCardDto> creditCardDtos){
        return creditCardDtos.stream().map(c -> {
            if(c.getId() != null){
                CreditCard creditCard = creditCardRepository.findById(c.getId()).orElseThrow(() -> new RuntimeException("Cartão de crédito não encontrado"));
                creditCard.setName(c.getName());
                creditCard.setNumber(c.getNumber());
                creditCard.setBrand(c.getBrand());
                creditCard.setSecurityCode(c.getSecurityCode());

                return creditCard;
            }

            return CreditCardMapper.toCreditCard(client, c);
        }).toList();
    }
}
