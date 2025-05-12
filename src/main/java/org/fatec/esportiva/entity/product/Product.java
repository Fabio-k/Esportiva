package org.fatec.esportiva.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.fatec.esportiva.listeners.LogListener;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Long id;

    @NotNull
    @Column(name = "pro_nome_produto")
    private String name;

    @NotNull
    @Column(name = "pro_data_entrada")
    private LocalDate entryDate;

    @NotNull
    @Column(name = "pro_quantidade_estoque")
    private int stockQuantity;

    @NotNull
    @Column(name = "pro_quantidade_bloqueada")
    private int blockedQuantity;

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

    // CascadeType.PERSIST: Se você salvar (persistir) uma entidade principal, as
    // entidades relacionadas também serão salvas automaticamente

    // CascadeType.MERGE: Se você atualizar (merge) uma entidade principal, as
    // entidades relacionadas também serão atualizadas automaticamente
    @Default
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "pertence", joinColumns = @JoinColumn(name = "per_pro_id"), inverseJoinColumns = @JoinColumn(name = "per_cat_id"))
    private List<ProductCategory> categories = new ArrayList<>();

    public BigDecimal getPriceWithMargin() {
        BigDecimal marginOfProfit = BigDecimal.ONE.add(pricingGroup.getProfitMargin());
        return costValue.multiply(marginOfProfit).setScale(2, RoundingMode.HALF_UP);
    }

    public void decreaseStock(int quantity) {
        if (quantity > stockQuantity || quantity > blockedQuantity)
            throw new RuntimeException("Conflito na quantidade de itens comprados");

        this.stockQuantity -= quantity;
        this.blockedQuantity -= quantity;
    }

    @Override
    public String toString() {
        return """
                Produto\n
                Nome: %s\n
                Data de cadastro: %s\n
                Qtd estoque: %s\n
                Qtd bloqueada: %s\n
                Custo: %s\n
                Status: %s\n
                Justificativa de inativação: %s\n
                Descrição: %s\n
                Imagem: %s
                """.formatted(
                name,
                entryDate.toString(),
                stockQuantity,
                blockedQuantity,
                costValue,
                status.getDisplayName(),
                inactivationJustification,
                description,
                image);
    }
}