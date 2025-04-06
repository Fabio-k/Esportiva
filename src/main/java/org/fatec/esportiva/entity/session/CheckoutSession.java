package org.fatec.esportiva.entity.session;

import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.entity.Address;
import org.fatec.esportiva.entity.CartItem;
import org.fatec.esportiva.entity.CreditCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckoutSession implements Serializable {
    private Long addressId;
    private List<Long> creditCardIds;
    private List<Long> exchangeVoucherIds;

    public CheckoutSession() {
        this.exchangeVoucherIds = new ArrayList<>();
        this.creditCardIds = new ArrayList<>();
    }
}
