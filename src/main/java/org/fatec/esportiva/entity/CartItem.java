package org.fatec.esportiva.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.esportiva.mapper.ProductMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Table(name = "itens_carrinho")
@NoArgsConstructor
@Getter
@Setter
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itc_id")
    private Long id;

    @Column(name = "itc_quantidade")
    @Min(0)
    @Max(1000)
    private Short quantity;

    @ManyToOne
    @JoinColumn(name = "itc_car_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "itc_pro_id")
    private Product product;

    public String getTotalPrice(){
        BigDecimal totalPrice = product.getPriceWithMargin().multiply(BigDecimal.valueOf(quantity));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return format.format(totalPrice);
    }
}
