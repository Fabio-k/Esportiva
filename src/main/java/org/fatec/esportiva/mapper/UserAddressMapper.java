package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.model.User;
import org.fatec.esportiva.model.UserAddress;
import org.fatec.esportiva.model.enums.AddressType;
import org.fatec.esportiva.request.AddressDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserAddressMapper {
    public List<UserAddress> toUserAddresses(User user, AddressDto addressDto){
        List<AddressType> addressTypes = Arrays.asList(AddressType.values());

        return  addressTypes.stream().filter(type -> addressDto.getTypes().contains(type))
                .map(type -> UserAddress.builder()
                        .address(AddressMapper.toAddress(addressDto))
                        .addressType(type)
                        .user(user)
                        .build()
                ).collect(Collectors.toList());
    }
}
