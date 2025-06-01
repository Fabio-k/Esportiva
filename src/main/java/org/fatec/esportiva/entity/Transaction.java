package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder.Default;
import org.fatec.esportiva.entity.address.Cep;
import org.fatec.esportiva.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tra_id")
    private Long id;

    @NotNull
    @Column(name = "tra_data_compra")
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tra_status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "tra_cli_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "tra_cep_id")
    private Cep cep;

    @NotNull
    @Column(name = "tra_numero_endereco")
    private String addressNumber;

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

    public BigDecimal getTotalCost(){
        return orders.stream().map(Order::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addOrder(Order order){
        if(order == null){
            throw new IllegalArgumentException("pedido não pode ser nulo");
        }
        orders.add(order);
        order.setTransaction(this);
    }
}
