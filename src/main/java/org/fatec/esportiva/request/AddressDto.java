package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.fatec.esportiva.entity.enums.AddressType;
import org.fatec.esportiva.entity.enums.ResidencyType;
import org.fatec.esportiva.entity.enums.StreetType;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;

    @NotBlank(message = "Endereço: Logradouro não deve ficar em branco")
    private String name;

    @NotBlank(message = "Endereço: CEP não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$|^[0-9]{8}$", message = "Endereço: O CEP deve estar em um formato válido: XXXXX-XXX | XXXXXXXX")
    private String cep;

    @NotNull(message = "Endereço: Tipo de residência não pode ficar em branco")
    private ResidencyType residencyType;

    @NotNull(message = "Endereço: Tipo de logradouro não pode ficar em branco")
    private StreetType streetType;

    @NotBlank(message = "Endereço: Nome do logradouro não pode ficar em branco")
    private String street;

    @NotBlank(message = "Endereço: Número não pode ficar em branco")
    @Pattern(regexp = "^[0-9]+$", message = "Endereço: Número somente aceita dígitos")
    private String number;

    @NotBlank(message = "Endereço: Bairro não pode ficar em branco")
    private String neighborhood;

    @NotBlank(message = "Endereço: Cidade não pode ficar em branco")
    private String city;

    @NotBlank(message = "Endereço: Estado não pode ficar em branco")
    private String state;

    @NotBlank(message = "Endereço: País não pode ficar em branco")
    private String country;

    private String observation;

    @NotEmpty(message = "Endereço deve pertencer ao menos a um tipo de residência")
    private HashSet<AddressType> types = new HashSet<>();
}
