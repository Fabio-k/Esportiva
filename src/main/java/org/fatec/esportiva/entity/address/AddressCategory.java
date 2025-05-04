package org.fatec.esportiva.entity.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "categorias_end")
public class AddressCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cae_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cae_nome")
    private AddressType addressType;

    @Default
    @ManyToMany(mappedBy = "addressCategories")
    private Set<Address> addresses = new HashSet<>();
}
