package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.enums.UserStatus;

import java.time.LocalDate;
import java.util.List;

public class ClientFactory {
    public static Client defaultClient(){
        Client client =  Client.builder()
                .cpf("55544433322")
                .name("tales")
                .dateBirth(LocalDate.of(2012, 12, 12))
                .email("tales@email.com")
                .gender(Gender.MASCULINO)
                .status(UserStatus.ATIVO)
                .telephone("11999993333")
                .telephoneType(PhoneType.TELEFONE)
                .build();

        Address address = AddressFactory.defaultAddress();
        address.setClient(client);
        client.setAddresses(List.of(address));

        return client;
    }
}
