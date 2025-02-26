package org.fatec.esportiva.request;

import lombok.*;
import org.fatec.esportiva.model.enums.AddressType;
import org.fatec.esportiva.model.enums.ResidencyType;
import org.fatec.esportiva.model.enums.StreetType;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String cep;
    private ResidencyType residencyType;
    private StreetType streetType;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String observation;
    private HashSet<AddressType> types = new HashSet<>();
}
