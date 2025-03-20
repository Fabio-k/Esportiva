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
}
