package org.fatec.esportiva.entity.product;

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
@Table(name = "categorias_produto")
public class ProductCategory {

    @Id
    @Column(name = "cat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cat_nome")
    private String name;

    // CascadeType.PERSIST: Se você salvar (persistir) uma entidade principal, as
    // entidades relacionadas também serão salvas automaticamente

    // CascadeType.MERGE: Se você atualizar (merge) uma entidade principal, as
    // entidades relacionadas também serão atualizadas automaticamente
    @Default
    @ManyToMany(mappedBy = "categories", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private List<Product> products = new ArrayList<>();
}
