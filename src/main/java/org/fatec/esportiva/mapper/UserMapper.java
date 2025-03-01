package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.request.UserDto;

@UtilityClass
public class UserMapper {

    public User toUser(UserDto userDto){
        return User.builder()
                .name(userDto.getName())
                .role(userDto.getRole())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .registrationNumber(userDto.getRegistrationNumber())
                .status(userDto.getStatus())
                .build();
    }

    public UserDto toUserDto(User user){
        return UserDto.builder()
                .role(user.getRole())
                .code(user.getCode())
                .status(user.getStatus())
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .registrationNumber(user.getRegistrationNumber())
                .build();
    }
}
