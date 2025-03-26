package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Column(name = "pro_nome_produto")
    private String name;

    @NotNull
    @Column(name = "pro_data_entrada")
    private Date entryDate;

    @NotNull
    @Column(name = "pro_quantidade_estoque")
    private int stockQuantity;

    @NotNull
    @Column(name = "pro_quantidade_bloqueada")
    private int blockedQuantity;

    @NotNull
    @Column(name = "pro_margem_lucro")
    private float profitMargin;

    @NotNull
    @Column(name = "pro_valor_custo")
    private float costValue;

    @NotNull
    @Column(name = "pro_categoria_inativacao")
    private ProductStatus inactivationCategory;

    @NotNull
    @Column(name = "pro_justificativa_inativacao")
    private String inactivationJustification;

    @ManyToOne
    @JoinColumn(name = "pro_grp_id")
    private PricingGroup pricingGroupId;

    // CascadeType.PERSIST: Se você salvar (persistir) uma entidade principal, as
    // entidades relacionadas também serão salvas automaticamente

    // CascadeType.MERGE: Se você atualizar (merge) uma entidade principal, as
    // entidades relacionadas também serão atualizadas automaticamente
    @Default
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "pertence", joinColumns = @JoinColumn(name = "pro_id"), inverseJoinColumns = @JoinColumn(name = "cat_id"))
    private List<ProductCategory> productCategories = new ArrayList<>();
}
