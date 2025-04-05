package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Column(name = "pro_valor_custo", precision = 10, scale = 2)
    private BigDecimal costValue;

    @NotNull
    @Column(name = "pro_categoria_inativacao")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @NotNull
    @Column(name = "pro_justificativa_inativacao")
    private String inactivationJustification;

    @Column(name = "pro_descricao")
    private String description;

    @Column(name = "pro_imagem")
    private String image;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_grp_id")
    private PricingGroup pricingGroup;

    @Default
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "pertence", joinColumns = @JoinColumn(name = "per_pro_id"), inverseJoinColumns = @JoinColumn(name = "per_cat_id"))
    private List<ProductCategory> categories = new ArrayList<>();

    public BigDecimal getPriceWithMargin(){
        BigDecimal marginOfProfit = BigDecimal.ONE.add(BigDecimal.valueOf(pricingGroup.getProfitMargin()));
        return costValue.multiply(marginOfProfit).setScale(2, RoundingMode.HALF_UP);
    }
}
