package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.request.AddressRequest;

@UtilityClass
public class AddressMapper {
    public Address toAddress(AddressRequest request){
        return Address.builder()
                .cep(request.cep())
                .city(request.city())
                .country(request.country())
                .state(request.state())
                .neighborhood(request.neighborhood())
                .number(request.number())
                .streetType(request.streetType())
                .residencyType(request.residencyType())
                .observation(request.observation())
                .build();
    }
}
