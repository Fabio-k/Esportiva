package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Cep;
import org.fatec.esportiva.request.AddressDto;

@UtilityClass
public class CepMapper {
    public Cep toCep(AddressDto address){
        return Cep.builder()
                .cep(address.getCep())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .neighborhood(address.getNeighborhood())
                .build();
    }
}
