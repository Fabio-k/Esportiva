package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cupons_promocao")
public class PromotionVoucher {

    @Id
    @Column(name = "cpr_id")
    private Long id;

    @NotNull
    @Column(name = "cpr_desconto_porcentagem")
    private BigDecimal discountPercentagem;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cpr_cli_id")
    private Client client;
}
