package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.Builder.Default;

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

    @NotBlank(message = "Frase de identificação não deve ficar em branco")
    private String addressIdentificationPhrase;

    @NotBlank(message = "CEP não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{8}$", message = "O CEP deve estar em um formato válido: XXXXXXXX")
    private String cep;

    @NotNull(message = "Tipo de residência não pode ficar em branco")
    private ResidencyType residencyType;

    @NotNull(message = "Tipo de logradouro não pode ficar em branco")
    private StreetType streetType;

    @NotBlank(message = "Nome do logradouro não pode ficar em branco")
    private String street;

    @NotBlank(message = "Número não pode ficar em branco")
    @Pattern(regexp = "^[0-9]+$", message = "Número somente aceita dígitos")
    private String number;

    @NotBlank(message = "Bairro não pode ficar em branco")
    private String neighborhood;

    @NotBlank(message = "Cidade não pode ficar em branco")
    private String city;

    @NotBlank(message = "Estado não pode ficar em branco")
    private String state;

    @NotBlank(message = "País não pode ficar em branco")
    private String country;

    private String observation;

    @Default
    @NotEmpty(message = "Endereço deve pertencer ao menos a um tipo de residência")
    private HashSet<AddressType> types = new HashSet<>();

    public void setCep(String cep) {
        if (cep != null) {
            this.cep = cep.replaceAll("\\D", ""); // Remove tudo que não é número
        }
    }
}
