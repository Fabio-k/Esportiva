package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.fatec.esportiva.entity.enums.CreditCardBrand;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cartoes_de_credito")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "car_numero")
    private String numero;

    @NotNull
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
