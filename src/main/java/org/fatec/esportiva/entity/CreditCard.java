package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.fatec.esportiva.entity.enums.CreditCardBrand;
import org.fatec.esportiva.listeners.LogListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cartoes_de_credito")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @NotNull
    @Column(name = "car_numero")
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_bandeira")
    private CreditCardBrand brand;

    @NotNull
    @Column(name = "car_nome_impresso")
    private String name;

    @NotNull
    @Column(name = "car_codigo_seguranca")
    private String securityCode;

    @Column(name = "car_temporario")
    private Boolean isTemporary;

    @Column(name = "car_expira_em")
    private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name = "car_cli_id")
    private Client client;

    @Override
    public String toString() {
        return """
                Cartão de crédito\n
                Nº cartão: %s\n
                Bandeira: %s\n
                Nome no cartão: %s\n
                CVV: %s
                """.formatted(
                number,
                brand.getDisplayName(),
                name,
                securityCode);
    }
}
