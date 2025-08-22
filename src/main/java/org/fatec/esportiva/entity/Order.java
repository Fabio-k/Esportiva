package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.product.Product;
import org.fatec.esportiva.listeners.LogListener;

import java.math.BigDecimal;

@Entity
@EntityListeners(LogListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pedidos")
public class Order implements totalPrice {

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

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "ped_pro_id")
	private Product product;

	public Boolean hasInsufficientQuantity(int tradedQuantity) {
		return this.quantity < tradedQuantity;
	}

	public Boolean isBeingTraded() {
		return status.isBeingTraded();
	}

	public boolean isInDeliveryProcess() {
		return status.isInDeliveryProcess();
	}

	@Override
	public BigDecimal getTotalPrice() {
		BigDecimal quantity = BigDecimal.valueOf(this.quantity);
		return product.getTotalPrice().multiply(quantity);
	}

	public void decreaseQuantity(int quantity) {
		this.quantity = this.quantity - quantity;
	}

}
