package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Table(name = "historico_compras")
@Getter
@Setter
@NoArgsConstructor
@Immutable
public class ProductHistory {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "cat_id")
    private Long categoryId;

    @Column(name = "cat_nome")
    private String categoryName;

    @Column(name = "pro_id")
    private Long productId;

    @Column(name = "pro_nome_produto")
    private String productName;

    @Column(name = "total_de_compras")
    private Integer totalOrders;

    @Column(name = "tra_data_compra")
    private LocalDate purchaseDate;

    @Column(name = "cep_estado")
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "ped_status")
    private OrderStatus status;
}
