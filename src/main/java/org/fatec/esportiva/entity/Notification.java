package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    public Notification(Client client, String message) {
        this.client = client;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Long id;

    @Column(name = "not_mensagem")
    private String message;

    @Column(name = "not_criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "not_visto")
    private Boolean isViewed = false;

    @ManyToOne
    @JoinColumn(name = "not_cli_id")
    private Client client;
}
