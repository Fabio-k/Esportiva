package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "logs")
public class Log {

    @Id
    @Column(name = "log_id")
    private Long id;

    @NotNull
    @Column(name = "log_operacao")
    private String operacao;

    @NotNull
    @Column(name = "log_usuario")
    private String usuario;

    @NotNull
    @Column(name = "log_data_hora")
    private LocalDateTime data_hora;

    @NotNull
    @Column(name = "log_conteudo_alteracao")
    private String conteudo_alteracao;
}
