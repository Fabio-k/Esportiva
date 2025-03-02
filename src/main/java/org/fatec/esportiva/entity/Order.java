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
    private int quantidade;

    @NotNull
    @Column(name = "transacoes_tra_id")
    private int transacoes_tra_id;

    @Column(name = "produtos_pro_id")
    private int produtos_pro_id;
}
