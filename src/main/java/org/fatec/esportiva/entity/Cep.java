package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cep")
public class Cep {

    @Id
    @Column(name = "cep_id")
    private int id;

    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$|^[0-9]{8}$", message = "Por favor, forneça um CEP válido")
    @NotNull
    @Column(name = "cep_cep")
    private String cep;

    @NotNull
    @Column(name = "cep_logradouro")
    private String logradouro;

    @NotNull
    @Column(name = "cep_bairro")
    private String bairro;

    @NotNull
    @Column(name = "cep_cidade")
    private String cidade;

    @NotNull
    @Column(name = "cep_estado")
    private String estado;

    @NotNull
    @Column(name = "cep_pais")
    private String pais;
}
