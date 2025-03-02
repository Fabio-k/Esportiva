package org.fatec.esportiva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;

import org.fatec.esportiva.model.enums.CreditCardBrand;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cartoes_de_credito")
public class CreditCard {

    public CreditCard(String numero,
            CreditCardBrand bandeira,
            String nome_impresso,
            int codigo_seguranca,
            boolean preferencial,
            int cli_id) {
        this.numero = numero;
        this.bandeira = bandeira;
        this.nome_impresso = nome_impresso;
        this.codigo_seguranca = codigo_seguranca;
        this.preferencial = preferencial;

    @Id
    @Column(name = "car_numero")
    private String numero;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "car_bandeira")
    private CreditCardBrand bandeira;

    @NotNull
    @Column(name = "car_nome_impresso")
    private String nome_impresso;

    @NotNull
    @Column(name = "car_codigo_seguranca")
    private int codigo_seguranca;

    @NotNull
    @Column(name = "car_preferencial")
    private boolean preferencial;

    @NotNull
    @Column(name = "clientes_cli_id")
    private int cli_id;
}
