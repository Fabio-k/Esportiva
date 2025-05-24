package org.fatec.esportiva.entity.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crr_id")
    private Long id;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "crr_cli_id")
    private Client client;

    @Column(name = "crr_criado_em")
    private LocalDateTime createdAt;

    @Column(name = "crr_notificado")
    private Boolean isNotified;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "produtos_removidos", joinColumns = @JoinColumn(name = "pre_crr_id"), inverseJoinColumns = @JoinColumn(name = "pre_pro_id"))
    private List<Product> removedProducts = new ArrayList<>();

    public BigDecimal getTotalPrice(){
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
