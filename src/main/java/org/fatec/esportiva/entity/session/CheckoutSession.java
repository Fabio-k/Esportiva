package org.fatec.esportiva.entity.session;

import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.dto.response.AddressResponseDto;
import org.fatec.esportiva.dto.response.SplitCreditCardResponseDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckoutSession implements Serializable {
    private AddressResponseDto address;
    private List<Long> exchangeVoucherIds;
    private String promotionalCouponCode;
    private List<SplitCreditCardResponseDto> creditCardPayments;

    public CheckoutSession() {
        this.exchangeVoucherIds = new ArrayList<>();
        this.creditCardPayments = new ArrayList<>();
    }

    public Boolean isUsingCreditCard(Long id){
        return creditCardPayments.stream().anyMatch(c -> c.getId().equals(id));
    }
}
