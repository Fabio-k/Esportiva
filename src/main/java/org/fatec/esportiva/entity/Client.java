package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.fatec.esportiva.entity.address.Address;
import org.fatec.esportiva.entity.cart.Cart;
import org.fatec.esportiva.entity.enums.Gender;
import org.fatec.esportiva.entity.enums.PhoneType;
import org.fatec.esportiva.entity.enums.UserStatus;
import org.fatec.esportiva.listeners.LogListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart = new Cart();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeVoucher> exchangeVouchers;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    // O dado não será salvo no banco de dados, ele é derivado de suas vendas
    @Transient
    private BigDecimal indexRanking;

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

    public BigDecimal calculateIndexRanking(){
        // O índice do cliente é calculado como o valor médio de suas transações
        // Assim dá para categorizar com um cliente que gasta mais ou menos dinheiro em média 
        BigDecimal transactionsCount = BigDecimal.ZERO;
        BigDecimal transactionTotal = BigDecimal.ZERO;

        for (Transaction transaction : this.transactions) {
            transactionTotal = transactionTotal.add(transaction.getTotalCost());
            transactionsCount = transactionsCount.add(BigDecimal.ONE);
        }

        // Para evitar divisão por zero se o cliente não tem compras, retorna zero
        if (transactionsCount == BigDecimal.ZERO){
            return BigDecimal.ZERO;
        }
        else{
            return transactionTotal.divide(transactionsCount, 2, RoundingMode.HALF_UP);
        }
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ATIVO;
    }

    @Override
    public String toString() {
        return """
                Cliente\n
                Nome: %s\n
                E-mail: %s\n
                CPF: %s\n
                Status: %s\n
                Gênero: %s\n
                Data de nascimento: %s\n
                Telefone: %s\n
                Tipo de telefone: %s
                """.formatted(
                name,
                email,
                cpf,
                status.getDisplayName(),
                gender.getDisplayName(),
                dateBirth.toString(),
                telephone,
                telephoneType.getDisplayName());
    }
}
