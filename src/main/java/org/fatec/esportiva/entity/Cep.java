package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import java.util.ArrayList;
import java.util.List;

import org.fatec.esportiva.listeners.LogListener;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cep")
public class Cep {

    @Id
    @Column(name = "cep_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cep_cep")
    private String cep;

    @NotNull
    @Column(name = "cep_logradouro")
    private String street;

    @NotNull
    @Column(name = "cep_bairro")
    private String neighborhood;

    @NotNull
    @Column(name = "cep_cidade")
    private String city;

    @NotNull
    @Column(name = "cep_estado")
    private String state;

    @NotNull
    @Column(name = "cep_pais")
    private String country;

    @Default
    @OneToMany(mappedBy = "cep")
    private List<Address> addresses = new ArrayList<>();

    @Override
    public String toString() {
        return """
                CEP\n
                CEP: %s\n
                Logradouro: %s\n
                Bairro: %s\n
                Cidade: %s\n
                Estado: %s\n
                País: %s
                """.formatted(
                cep,
                street,
                neighborhood,
                city,
                state,
                country);
    }
}
