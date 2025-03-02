package org.fatec.esportiva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.time.LocalDateTime;

import org.fatec.esportiva.model.enums.OrderStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pedidos")
public class Order {

    public Order(
            OrderStatus status,
            int quantidade,
            int transacoes_tra_id,
            int produtos_pro_id) {
        this.status = status;
        this.quantidade = quantidade;
        this.transacoes_tra_id = transacoes_tra_id;
        this.produtos_pro_id = produtos_pro_id;
    }

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
