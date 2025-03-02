package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.fatec.esportiva.entity.enums.ProductStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "produtos")
public class Product {

    public Product(
            int id,
            int quantidade_estoque,
            int quantidade_bloqueada,
            float valor_precificacao,
            String justificativa_inativacao,
            ProductStatus categoria_inativacao,
            float valor_custo,
            Date data_entrada,
            String nome_produto,
            int grupo_precificacao_grp_id) {
        this.id = id;
        this.quantidade_estoque = quantidade_estoque;
        this.quantidade_bloqueada = quantidade_bloqueada;
        this.valor_precificacao = valor_precificacao;
        this.justificativa_inativacao = justificativa_inativacao;
        this.categoria_inativacao = categoria_inativacao;
        this.valor_custo = valor_custo;
        this.data_entrada = data_entrada;
        this.nome_produto = nome_produto;
        this.grupo_precificacao_grp_id = grupo_precificacao_grp_id;
    }

    @Id
    @Column(name = "pro_id")
    private int id;

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

    @Column(name = "grupo_precificacao_grp_id")
    private int grupo_precificacao_grp_id;
}
