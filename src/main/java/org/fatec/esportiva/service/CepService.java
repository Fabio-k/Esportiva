package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Cep;
import org.fatec.esportiva.mapper.CepMapper;
import org.fatec.esportiva.repository.CepRepository;
import org.fatec.esportiva.dto.request.AddressDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CepService {
    private final CepRepository cepRepository;

    @Transactional
    public Cep findOrCreateByCep(AddressDto addressDto){
        return cepRepository.findByCep(addressDto.getCep()).orElseGet(() ->
                cepRepository.save(CepMapper.toCep(addressDto))
        );
    }
}
