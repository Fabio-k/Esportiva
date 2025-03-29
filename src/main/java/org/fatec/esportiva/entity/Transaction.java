package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import org.fatec.esportiva.entity.enums.OrderStatus;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transacoes")
public class Transaction {

    @Id
    @Column(name = "tra_id")
    private Long id;

    @NotNull
    @Column(name = "tra_data_compra")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tra_status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "tra_cli_id")
    private Client client;
}
