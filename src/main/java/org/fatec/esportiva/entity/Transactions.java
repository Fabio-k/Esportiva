package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transacoes")
public class Transactions {

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
