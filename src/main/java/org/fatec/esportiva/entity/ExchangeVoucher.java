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
@Table(name = "cupons_troca")
public class ExchangeVoucher {

    public ExchangeVoucher(
            int id,
            float valor,
            int quantidade,
            int clientes_cli_id) {
        this.id = id;
        this.valor = valor;
        this.quantidade = quantidade;
        this.clientes_cli_id = clientes_cli_id;
    }

    @Id
    @Column(name = "ctr_id")
    private int id;

    @NotNull
    @Column(name = "ctr_valor")
    private float valor;

    @NotNull
    @Column(name = "ctr_quantidade")
    private int quantidade;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;
}
