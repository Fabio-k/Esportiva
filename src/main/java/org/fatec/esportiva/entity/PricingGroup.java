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
    private int id;

    @NotNull
    @Column(name = "grp_valor_precificacao")
    private float valor_precificacao;

    @NotNull
    @Column(name = "grp_nome")
    private String nome;
}
