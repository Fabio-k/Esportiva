package org.fatec.esportiva.request;

import lombok.*;
import org.fatec.esportiva.model.enums.Gender;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Role role;
    private String name;
    private String email;
    private String code;
    private String registrationNumber;
    private UserStatus status;
    private Gender gender;
    private List<AddressDto> addresses = new ArrayList<>();
}
