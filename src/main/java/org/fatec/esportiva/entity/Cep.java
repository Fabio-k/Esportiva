package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "cep")
public class Cep {

    public Cep(
            int id,
            String cep,
            String logradouro,
            String bairro,
            String cidade,
            String estado,
            String pais) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    @Id
    @Column(name = "cep_id")
    private int id;

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
