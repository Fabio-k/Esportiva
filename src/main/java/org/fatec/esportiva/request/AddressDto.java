package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "CEP não pode ficar em branco")
    private String cep;

    @NotNull(message = "tipo de residência não pode ficar em branco")
    private ResidencyType residencyType;

    @NotNull(message = "tipo de logradouro não pode ficar em branco")
    private StreetType streetType;

    @NotBlank(message = "Nome do logradouro não pode ficar em branco")
    private String street;

    @NotBlank(message = "Numero não pode ficar em branco")
    private String number;

    @NotBlank(message = "Bairro não pode ficar em branco")
    private String neighborhood;

    @NotBlank(message = "Cidade não pode ficar em branco")
    private String city;

    @NotBlank(message = "Estado não pode ficar em branco")
    private String state;

    @NotBlank(message = "Pais não pode ficar em branco")
    private String country;
    private String observation;

    @NotEmpty(message = "Endereço deve pertencer ao menos a um tipo de residência")
    private HashSet<AddressType> types = new HashSet<>();
}
