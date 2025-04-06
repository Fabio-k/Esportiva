package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.PricingGroup;

import org.fatec.esportiva.request.PricingGroupDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PricingGroupMapper {
    public PricingGroup toPricingGroup(PricingGroupDto pricingGroupDto) {
        return PricingGroup.builder()
                .name(pricingGroupDto.getName())
                .profitMargin(pricingGroupDto.getProfitMargin())
                .build();
    }

    public PricingGroupDto toPricingGroupDto(PricingGroup pricingGroup) {
        return PricingGroupDto.builder()
                .id(pricingGroup.getId())
                .name(pricingGroup.getName())
                .profitMargin(pricingGroup.getProfitMargin())
                .build();
    }
}
