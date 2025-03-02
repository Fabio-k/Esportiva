package org.fatec.esportiva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "grupo_precificacao")
public class PricingGroup {

    public PricingGroup(
            int id,
            float precificacao,
            String nome) {
        this.id = id;
        this.valor_precificacao = precificacao;
        this.nome = nome;
    }

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
