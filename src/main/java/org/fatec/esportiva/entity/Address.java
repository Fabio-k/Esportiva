package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.entity.enums.AddressType;
import org.fatec.esportiva.entity.enums.ResidencyType;
import org.fatec.esportiva.entity.enums.StreetType;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "end_numero")
    private String number;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_residencia")
    private ResidencyType residencyType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_logradouro")
    private StreetType streetType;

    @NotNull
    @Column(name = "end_frase_identificacao")
    private String identificationPhrase;

    @Column(name = "end_observacao")
    private String observation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "clientes_cli_id")
    private Clients clients;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cep_cep_id")
    private Cep cep;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "address_type", joinColumns = @JoinColumn(name = "address_id"))
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private List<AddressType> addressTypeList = new ArrayList<>();

}
