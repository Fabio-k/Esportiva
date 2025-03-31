package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.listeners.LogListener;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transacoes")
public class Transaction {

    @Id
    @Column(name = "tra_id")
    private Long id;

    @NotNull
    @Column(name = "tra_data_compra")
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tra_status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "tra_cli_id")
    private Client client;

    @Default
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return """
                Transação\n
                ID: %s\n
                Status: %s\n
                Data da compra: %s
                """.formatted(
                id,
                status.getDisplayName(),
                purchaseDate.toString());
    }
}
