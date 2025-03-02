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
@Table(name = "categorias_produto")
public class ProductCategory {

    public ProductCategory(int id,
            String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Id
    @Column(name = "cat_id")
    private int id;

    @NotNull
    @Column(name = "cat_nome")
    private String nome;
}
