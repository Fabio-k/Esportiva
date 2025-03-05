package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.entity.enums.ResidencyType;
import org.fatec.esportiva.entity.enums.StreetType;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "enderecos")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Long id;

    @NotNull
    @Column(name = "end_frase_identificacao")
    private String name;

    @NotNull
    @Column(name = "end_numero")
    private String number;

    @Column(name = "end_observacao")
    private String observation;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_residencia")
    private ResidencyType residencyType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_logradouro")
    private StreetType streetType;

    @ManyToOne
    @JoinColumn(name = "end_cep_id")
    private Cep cep;

    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    private Client client;

    @ManyToMany
    @JoinTable(name = "funcao", joinColumns = { @JoinColumn(name = "fun_end_id") }, inverseJoinColumns = {
            @JoinColumn(name = "fun_cae_id") })
    Set<AddressCategory> addressCategories = new HashSet<>();
}
