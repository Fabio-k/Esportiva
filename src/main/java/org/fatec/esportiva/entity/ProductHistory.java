package org.fatec.esportiva.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "historico_compras")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "pro_nome")
    private String productName;

    @Column(name = "total_de_compras")
    private Integer totalOrders;

    @Column(name = "tra_data_compra")
    private LocalDate purchaseDate;
}
