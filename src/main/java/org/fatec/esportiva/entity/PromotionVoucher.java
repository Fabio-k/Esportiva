package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cupons_promocao")
public class PromotionVoucher {

    public PromotionVoucher(
            int id,
            float promocao_porcentagem,
            int clientes_cli_id,
            int produtos_pro_id) {
        this.id = id;
        this.promocao_porcentagem = promocao_porcentagem;
        this.clientes_cli_id = clientes_cli_id;
        this.produtos_pro_id = produtos_pro_id;
    }

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
