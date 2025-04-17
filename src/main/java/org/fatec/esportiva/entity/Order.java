package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.listeners.LogListener;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pedidos")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ped_status")
    private OrderStatus status;

    @NotNull
    @Column(name = "ped_quantidade")
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ped_tra_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "ped_pro_id")
    private Product product;

    @Override
    public String toString() {
        return """
                Pedido\n
                Produto: %s\n
                Status: %s\n
                Quantidade: %s\n
                Transação: %s
                """.formatted(
                product.getName(),
                status.getDisplayName(),
                quantity,
                transaction.getId());
    }

    public Boolean hasInsufficientQuantity(int tradedQuantity){
        return this.quantity < tradedQuantity;
    }


    public Boolean isBeingTraded(){
        return status.isBeingTraded();
    }

    public boolean isInDeliveryProcess() {
        return status.isInDeliveryProcess();
    }
}
