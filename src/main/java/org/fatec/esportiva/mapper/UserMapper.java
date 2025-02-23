package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.request.UserRequest;
import org.fatec.esportiva.response.UserResponse;

@UtilityClass
public class UserMapper {
    public User toUser(UserRequest request){
        return User.builder()
                .name(request.name())
                .role(request.role())
                .email(request.email())
                .gender(request.gender())
                .password(request.password())
                .registrationNumber(request.registrationNumber())
                .build();
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
