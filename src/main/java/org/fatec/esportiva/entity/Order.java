package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.entity.enums.OrderStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pedidos")
public class Order {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "ped_status")
    private OrderStatus status;

    @NotNull
    @Column(name = "ped_quantidade")
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ped_tra_id")
    private Transactions transaction;

    @ManyToOne
    @JoinColumn(name = "ped_pro_id")
    private Product product;
}
