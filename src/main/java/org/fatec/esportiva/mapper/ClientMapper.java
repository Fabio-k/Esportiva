package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.dto.request.ClientDto;

@UtilityClass
public class ClientMapper {

    public Client toUser(ClientDto userDto) {
        return Client.builder()
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

    public ClientDto toUserDto(Client user) {
        return ClientDto.builder()
                .id(user.getId())
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
