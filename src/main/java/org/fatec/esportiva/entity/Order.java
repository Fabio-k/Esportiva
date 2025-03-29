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
    @Column(name = "ped_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ped_status")
    private OrderStatus status;

    @NotNull
    @Column(name = "ped_quantidade")
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ped_tra_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "ped_pro_id")
    private Product product;
}
