package org.fatec.esportiva.entity;

import java.math.BigDecimal;

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
    @Column(name = "ctr_valor", precision = 10, scale = 2)
    private BigDecimal value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ctr_cli_id")
    private Client client;
}
