package org.fatec.esportiva.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.model.enums.Gender;
import org.fatec.esportiva.model.enums.Role;
import org.fatec.esportiva.model.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {

    public User(Role role, String name, String email, String password, String code, String registrationNumber, Status status, Gender gender) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.registrationNumber = registrationNumber;
        this.status = status;
        this.gender = gender;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String name;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String code;

    @NotNull
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
