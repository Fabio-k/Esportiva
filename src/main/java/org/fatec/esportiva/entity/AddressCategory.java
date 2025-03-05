package org.fatec.esportiva.entity;

import org.fatec.esportiva.entity.enums.AddressType;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cae_nome")
    private AddressType addressType;
}
