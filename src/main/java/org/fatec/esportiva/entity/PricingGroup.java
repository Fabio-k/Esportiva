package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "grupo_precificacao")
public class PricingGroup {

    @Id
    @Column(name = "grp_id")
    private Long id;

    @NotNull
    @Column(name = "grp_nome")
    private String name;

    @NotNull
    @Column(name = "grp_margem_lucro")
    private float profitMargin;
}
