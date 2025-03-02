package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.fatec.esportiva.entity.enums.ProductStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "telefones")
public class Telephone {

    public Telephone(
            String telefone,
            int clientes_cli_id,
            String tipo) {
        this.telefone = telefone;
        this.clientes_cli_id = clientes_cli_id;
        this.tipo = tipo;
    }

    @Id
    @Column(name = "tel_telefone")
    private String telefone;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;

    @NotNull
    @Column(name = "tel_tipo")
    private String tipo;
}
