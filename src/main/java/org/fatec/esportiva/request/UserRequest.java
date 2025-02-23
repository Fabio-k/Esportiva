package org.fatec.esportiva.request;

import org.fatec.esportiva.model.Address;
import org.fatec.esportiva.model.enums.Gender;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.Status;

public record UserRequest(Role role, String name, String email, String password, String code, String registrationNumber, Status status, Gender gender, AddressRequest address) {
}
