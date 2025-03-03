package org.fatec.esportiva.request;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.Role;
import org.fatec.esportiva.entity.enums.UserStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    @NotBlank
    private Long id;

    @NotBlank(message = "Nome não pode ficar em branco")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome deve conter apenas letras e espaços")
    private String name;

    @NotBlank(message = "email não pode ficar em branco")
    @Email(message = "email deve ser válido")
    private String email;

    @NotBlank(message = "CPF não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotNull
    private UserStatus status;

    @NotNull(message = "Gênero não pode ficar em branco")
    private Gender gender;

    @NotBlank
    @Past(message = "A data de nascimento deve estar no passado!")
    private Date dateBirth;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$|^\\d{11}$", message = "Por favor, forneça um número de telefone válido")
    private String telephone;

    @NotBlank
    private String telephoneType;

    @Valid
    private List<AddressDto> addresses = new ArrayList<>();
}
