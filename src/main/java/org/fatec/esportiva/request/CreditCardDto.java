package org.fatec.esportiva.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.fatec.esportiva.entity.enums.CreditCardBrand;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class CreditCardDto {

    @NotBlank
    private Long id;

    @Pattern(regexp = "^[0-9]{13,19}$", message = "O campo deve conter um número de cartão de crédito válido")
    @NotBlank
    private String numero;

    @NotBlank
    private CreditCardBrand bandeira;

    @NotBlank
    private String nome_impresso;

    @Pattern(regexp = "^[0-9]+$", message = "O código de segurança somente contém dígitos")
    @NotBlank
    private int codigo_seguranca;

    @NotBlank
    private boolean preferencial;
}
