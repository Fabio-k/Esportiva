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
    @Column(name = "cae_cat_end")
    private AddressType addressType;
}
