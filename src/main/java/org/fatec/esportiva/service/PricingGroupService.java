package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.mapper.PricingGroupMapper;
import org.fatec.esportiva.repository.PricingGroupRepository;
import org.fatec.esportiva.request.PricingGroupDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingGroupService {
    private final PricingGroupRepository pricingGroupRepository;

    public List<PricingGroupDto> getPrincingGroups() {
        List<PricingGroupDto> products = pricingGroupRepository.findAll().stream()
                .map(PricingGroupMapper::toPricingGroupDto).toList();
        return products;
    }
}
