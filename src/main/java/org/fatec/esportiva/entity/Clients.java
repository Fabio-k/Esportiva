package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "clientes")
public class Clients implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private Long id;

    @NotNull
    @Column(name = "cli_nome")
    private String name;

    @NotNull
    @Column(name = "cli_email")
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
    private Date dateBirth;

    @NotNull
    @Column(name = "cli_telefone")
    private String telephone;

    @NotNull
    @Column(name = "cli_telefone_tipo")
    private String telephoneType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
