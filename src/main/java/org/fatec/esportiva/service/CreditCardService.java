package org.fatec.esportiva.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.CreditCard;
import org.fatec.esportiva.mapper.CreditCardMapper;
import org.fatec.esportiva.repository.CreditCardRepository;
import org.fatec.esportiva.dto.request.CreditCardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

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

    public CreditCardDto saveCreditCard(CreditCardDto dto){
        return CreditCardMapper.toCreditCardDto(creditCardRepository.save(CreditCardMapper.toCreditCard(dto)));
    }
}
