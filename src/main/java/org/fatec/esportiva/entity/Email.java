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
@Table(name = "emails")
public class Email {

    @Id
    @Column(name = "ema_email")
    private String email;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int clientes_cli_id;
}
