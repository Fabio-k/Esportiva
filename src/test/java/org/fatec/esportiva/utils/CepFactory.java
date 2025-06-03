package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.address.Cep;

public class CepFactory {
    public static Cep defaultCep(){
        return Cep.builder()
                .cep("52191440")
                .street("Rua Só Nós Dois")
                .neighborhood("Brejo de Beberibe")
                .city("Recife")
                .state("Pernambuco")
                .country("Brasil")
                .build();
    }
}
