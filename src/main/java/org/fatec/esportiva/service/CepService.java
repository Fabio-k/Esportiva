package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cep;
import org.fatec.esportiva.mapper.CepMapper;
import org.fatec.esportiva.repository.CepRepository;
import org.fatec.esportiva.request.AddressDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CepService {
    private final CepRepository cepRepository;

    public Cep findOrCreateByCep(AddressDto addressDto){
        return cepRepository.findByCep(addressDto.getCep()).orElse(
                cepRepository.save(CepMapper.toCep(addressDto))
        );
    }
}
