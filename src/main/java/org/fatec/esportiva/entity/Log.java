package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "logs")
public class Log {

    public Log(
            int id,
            String operacao,
            String usuario,
            LocalDateTime data_hora,
            String conteudo_alteracao) {
        this.id = id;
        this.operacao = operacao;
        this.usuario = usuario;
        this.data_hora = data_hora;
        this.conteudo_alteracao = conteudo_alteracao;
    }

    @Id
    @Column(name = "log_id")
    private int id;

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
