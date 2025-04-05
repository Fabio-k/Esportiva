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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @NotNull
    @Column(name = "log_usuario")
    private String user;

    @NotNull
    @Column(name = "log_data_hora")
    private LocalDateTime timestamp;

    @NotNull
    @Column(name = "log_operacao")
    private String operation;

    @NotNull
    @Column(name = "log_conteudo_alteracao")
    private String operationContent;
}
