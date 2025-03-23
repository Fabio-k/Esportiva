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
@Table(name = "categorias_produto")
public class ProductCategory {

    @Id
    @Column(name = "cat_id")
    private Long id;

    @NotNull
    @Column(name = "cat_nome")
    private String name;

    @Default
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "pertence", joinColumns = @JoinColumn(name = "cat_id"), inverseJoinColumns = @JoinColumn(name = "pro_id"))
    private List<Product> products = new ArrayList<>();
}
