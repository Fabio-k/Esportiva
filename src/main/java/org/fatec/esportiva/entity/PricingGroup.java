package org.fatec.esportiva.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

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

    @Default
    @Column
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pro_id")
    private List<Product> products = new ArrayList<>();
}
