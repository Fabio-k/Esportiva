package org.fatec.esportiva.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    private Long id;

    // À-ÿ -> Permite inserir os caracteres com acentos, por exemplo: João
    @NotBlank(message = "Nome não pode ficar em branco")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
    private String name;

    @NotBlank(message = "E-mail não pode ficar em branco")
    @Email(message = "E-mail deve ser em um formato válido: <nome>@<domínio>")
    @Pattern(regexp = "^(?!.*@esportiva).*", message = "O e-mail não deve ser do domínio 'esportiva'. Ele é reservado somente para os administradores!")
    private String email;

    @NotBlank(message = "CPF não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotNull(message = "Status não pode ficar em branco")
    private UserStatus status;

    @NotNull(message = "Gênero não pode ficar em branco")
    private Gender gender;

    @NotNull(message = "A data de nascimento não pode ficar em branco")
    @Past(message = "A data de nascimento deve estar no passado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;

    @NotBlank(message = "O número de telefone não pode ficar em branco")
    private String telephone;

    @NotNull(message = "O tipo de telefone não pode ficar em branco")
    private PhoneType telephoneType;

    @Valid
    @Default
    private List<AddressDto> addresses = new ArrayList<>();

    @Valid
    @Default
    private List<CreditCardDto> creditCards = new ArrayList<>();

    @Valid
    @Default
    private List<ExchangeVoucherDto> exchangeVoucherDtos = new ArrayList<>();

    public String displayDateBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateBirth.format(formatter);
    }

    public void setTelephone(String telefone) {
        if (telefone != null) {
            this.telephone = telefone.replaceAll("\\D", ""); // Remove tudo que não é número
        }
    }

    public void setCpf(String cpf) {
        if (cpf != null) {
            this.cpf = cpf.replaceAll("\\D", ""); // Remove tudo que não é número
        }
    }
}
