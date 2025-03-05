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
    private float promocao_porcentagem;

    @NotNull
    @Column(name = "cli_cli_id")
    private Long cli_cli_id;

    @NotNull
    @Column(name = "pro_pro_id")
    private Long pro_pro_id;
}
