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
@Table(name = "categorias_produto")
public class ProductCategory {

    @Id
    @Column(name = "cat_id")
    private Long id;

    @NotNull
    @Column(name = "cat_nome")
    private String nome;
}
