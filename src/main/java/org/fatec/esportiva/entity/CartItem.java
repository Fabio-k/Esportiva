package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.esportiva.mapper.ProductMapper;

@Entity
@Table(name = "itens_carrinho")
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itc_id")
    private Long id;

    @Column(name = "itc_quantidade")
    @Min(0)
    @Max(1000)
    private Short quantity;

    @ManyToOne
    @JoinColumn(name = "itc_car_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "itc_pro_id")
    private Product product;
}
