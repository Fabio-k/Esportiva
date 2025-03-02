package org.fatec.esportiva.model;

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
@Table(name = "emails")
public class Email {

    public PromotionVoucher(
            String email,
            int clientes_cli_id) {
        this.email = email;
        this.clientes_cli_id = clientes_cli_id;
    }

    @Id
    @Column(name = "ema_email")
    private String email;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;
}
