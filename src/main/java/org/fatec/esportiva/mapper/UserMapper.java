package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.request.UserDto;
import org.fatec.esportiva.response.UserResponse;

@UtilityClass
public class UserMapper {

    public User toUser(UserDto userDto){
        return User.builder()
                .name(userDto.getName())
                .role(userDto.getRole())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .registrationNumber(userDto.getRegistrationNumber())
                .build();
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
