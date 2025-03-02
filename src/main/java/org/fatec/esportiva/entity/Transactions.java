package org.fatec.esportiva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.fatec.esportiva.model.enums.ProductStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transacoes")
public class Transactions {

    public Transactions(
            int id,
            Date data_compra,
            int clientes_cli_id) {
        this.id = id;
        this.data_compra = data_compra;
        this.clientes_cli_id = clientes_cli_id;
    }

    @Id
    @Column(name = "tra_id")
    private int id;

    @NotNull
    @Column(name = "tra_data_compra")
    private Date data_compra;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;
}
