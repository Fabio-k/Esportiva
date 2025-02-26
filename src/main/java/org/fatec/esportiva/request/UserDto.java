package org.fatec.esportiva.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.esportiva.model.enums.Gender;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.Status;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Role role;
    private String name;
    private String email;
    private String password;
    private String code;
    private String registrationNumber;
    private Status status;
    private Gender gender;
    private List<AddressDto> addresses = new ArrayList<>();
}
