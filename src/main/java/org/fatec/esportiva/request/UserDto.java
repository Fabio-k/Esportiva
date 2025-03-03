package org.fatec.esportiva.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.Role;
import org.fatec.esportiva.entity.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Nome não pode ficar em branco")
    private String name;

    @NotBlank(message = "email não pode ficar em branco")
    @Email(message = "email deve ser válido")
    private String email;

    private String code;

    @NotBlank(message = "CPF não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter 11 dígitos numéricos")
    private String registrationNumber;

    private UserStatus status;

    @NotNull(message = "Gênero não pode ficar em branco")
    private Gender gender;

    @Valid
    private List<AddressDto> addresses = new ArrayList<>();
}
