package org.fatec.esportiva.entity.product;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;
import java.math.BigDecimal;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "grp_nome")
    private String name;

    @NotNull
    @Column(name = "grp_margem_lucro", precision = 10, scale = 2)
    private BigDecimal profitMargin;

    @Default
    @OneToMany(mappedBy = "pricingGroup", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
