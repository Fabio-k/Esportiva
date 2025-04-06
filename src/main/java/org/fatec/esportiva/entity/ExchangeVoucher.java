package org.fatec.esportiva.entity;

import java.math.BigDecimal;

import org.fatec.esportiva.listeners.LogListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cupons_troca")
public class ExchangeVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctr_id")
    private Long id;

    @NotNull
    @Column(name = "ctr_valor", precision = 10, scale = 2)
    private BigDecimal value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ctr_cli_id")
    private Client client;

    @Override
    public String toString() {
        return """
                Cupom de troca\n
                ID: %s\n
                Valor: %s
                """.formatted(
                id,
                value);
    }
}
