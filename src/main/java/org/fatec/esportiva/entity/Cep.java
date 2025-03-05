package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "cep")
    private List<Address> addresses = new ArrayList<>();
}
