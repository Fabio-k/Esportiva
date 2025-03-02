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
@Table(name = "telefones")
public class Telephone {

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
