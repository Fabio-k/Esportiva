package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cupons_troca")
public class ExchangeVoucher {

    @Id
    @Column(name = "ctr_id")
    private Long id;

    @NotNull
    @Column(name = "ctr_valor")
    private float value;

    @NotNull
    @Column(name = "ctr_quantidade")
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cli_cli_id")
    private Client client;
}
