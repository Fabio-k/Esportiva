package org.fatec.esportiva.entity.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.listeners.LogListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(LogListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "enderecos")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Long id;

    @NotNull
    @Column(name = "end_frase_identificacao")
    private String addressIdentificationPhrase;

    @NotNull
    @Column(name = "end_numero")
    private String number;

    @Column(name = "end_observacao")
    private String observation;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_residencia")
    private ResidencyType residencyType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "end_tipo_logradouro")
    private StreetType streetType;

    @Column(name = "end_temporario")
    private Boolean temporary;

    @Column(name = "end_expira_em")
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "end_cep_id")
    private Cep cep;

    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    private Client client;

    @Default
    @ManyToMany
    @JoinTable(name = "funcao", joinColumns = { @JoinColumn(name = "fun_end_id") }, inverseJoinColumns = {
            @JoinColumn(name = "fun_cae_id") })

    Set<AddressCategory> addressCategories = new HashSet<>();

    @Override
    public String toString() {
        return """
                Endereço\n
                Frase de identificação: %s\n
                Número: %s\n
                Observação: %s\n
                Tipo de residência: %s\n
                Tipo de rua: %s
                """.formatted(
                addressIdentificationPhrase,
                number,
                observation,
                residencyType.getDisplayName(),
                streetType.getDisplayName());
    }
}
