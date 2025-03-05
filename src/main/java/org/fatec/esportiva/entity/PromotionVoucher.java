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
@Table(name = "cupons_promocao")
public class PromotionVoucher {

    @Id
    @Column(name = "cpr_id")
    private Long id;

    @NotNull
    @Column(name = "cpr_promocao_porcentagem")
    private float discountPercentagem;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cli_cli_id")
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pro_pro_id")
    private Product product;
}
