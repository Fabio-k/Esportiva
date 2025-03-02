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
    private int id;

    @NotNull
    @Column(name = "cpr_promocao_porcentagem")
    private float promocao_porcentagem;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;

    @NotNull
    @Column(name = "produtos_pro_id")
    private int produtos_pro_id;
}
