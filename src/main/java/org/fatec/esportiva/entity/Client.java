package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.listeners.LogListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@EntityListeners(LogListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "clientes")
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private Long id;

    @NotNull
    @Column(name = "cli_nome")
    private String name;

    @NotNull
    @Column(name = "cli_email", unique = true)
    private String email;

    @NotNull
    @Column(name = "cli_cpf")
    private String cpf;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "cli_status")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "cli_genero")
    private Gender gender;

    @NotNull
    @Column(name = "cli_data_nascimento")
    private LocalDate dateBirth;

    @NotNull
    @Column(name = "cli_telefone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "cli_telefone_tipo")
    private PhoneType telephoneType;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeVoucher> exchangeVouchers;

    @NotNull
    @Column(name = "cli_endereco_preferencial")
    private Long preferentialAddress;

    @NotNull
    @Column(name = "cli_cartao_preferencial")
    private Long preferentialCard;

    // O dado não será salvo no banco de dados, ele é derivado de suas vendas
    @Transient
    private float indexRanking;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ATIVO;
    }

    @Override
    public String toString() {
        return "Nome: " + name;
    }
}
