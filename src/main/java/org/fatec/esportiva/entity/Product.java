package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

import org.fatec.esportiva.entity.enums.ProductStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "produtos")
public class Product {

    @Id
    @Column(name = "pro_id")
    private Long id;

    @NotNull
    @Column(name = "pro_quantidade_estoque")
    private int quantidade_estoque;

    @NotNull
    @Column(name = "pro_quantidade_bloqueada")
    private int quantidade_bloqueada;

    @NotNull
    @Column(name = "pro_valor_precificacao")
    private float valor_precificacao;

    @NotNull
    @Column(name = "pro_justificativa_inativacao")
    private String justificativa_inativacao;

    @NotNull
    @Column(name = "pro_categoria_inativacao")
    private ProductStatus categoria_inativacao;

    @NotNull
    @Column(name = "pro_valor_custo")
    private float valor_custo;

    @NotNull
    @Column(name = "pro_data_entrada")
    private Date data_entrada;

    @NotNull
    @Column(name = "pro_nome_produto")
    private String nome_produto;

    @ManyToOne
    @JoinColumn(name = "grp_grp_id")
    private PricingGroup pricingGroup;
}
