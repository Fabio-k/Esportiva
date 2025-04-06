package org.fatec.esportiva.entity.session;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckoutSession implements Serializable {
    private Long addressId;
    private List<Long> creditCardIds;

    public CheckoutSession() {
        this.creditCardIds = new ArrayList<>();
    }
}
