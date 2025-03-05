package org.fatec.esportiva.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
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

    private UserStatus status;

    @NotNull(message = "Gênero não pode ficar em branco")
    private Gender gender;

    @Past(message = "A data de nascimento deve estar no passado!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$|^\\d{11}$", message = "Por favor, forneça um número de telefone válido")
    private String telephone;

    private String telephoneType;

    @Valid
    private List<AddressDto> addresses = new ArrayList<>();

    @Valid
    private List<CreditCardDto> creditCards = new ArrayList<>();
}
