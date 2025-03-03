package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Clients;
import org.fatec.esportiva.request.ClientDto;

@UtilityClass
public class ClientMapper {

    public Clients toUser(ClientDto userDto) {
        return Clients.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .cpf(userDto.getCpf())
                .status(userDto.getStatus())
                .gender(userDto.getGender())
                .dateBirth(userDto.getDateBirth())
                .telephone(userDto.getTelephone())
                .telephoneType(userDto.getTelephoneType())
                .build();
    }

    public ClientDto toUserDto(Clients user) {
        return ClientDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .status(user.getStatus())
                .gender(user.getGender())
                .dateBirth(user.getDateBirth())
                .telephone(user.getTelephone())
                .telephoneType(user.getTelephoneType())
                .build();
    }
}
