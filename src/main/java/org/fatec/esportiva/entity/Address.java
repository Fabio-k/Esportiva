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
public class Address {

    public Address(String cep, ResidencyType residencyType, StreetType streetType, String number, String neighborhood, String city, String state, String country, String observation) {
        this.cep = cep;
        this.residencyType = residencyType;
        this.streetType = streetType;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.observation = observation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cep;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "residency_type")
    private ResidencyType residencyType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "street_type")
    private StreetType streetType;

    @NotNull
    private String number;

    @NotNull
    private String street;

    @NotNull
    private String neighborhood;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "address_type", joinColumns = @JoinColumn(name = "address_id"))
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private List<AddressType> addressTypeList = new ArrayList<>();

}
