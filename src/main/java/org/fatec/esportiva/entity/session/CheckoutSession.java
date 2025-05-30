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
    private List<Long> creditCardIds;
    private List<Long> exchangeVoucherIds;
    private String promotionalCouponCode;
    private List<SplitCreditCardResponseDto> creditCardPayments = new ArrayList<>();

    public CheckoutSession() {
        this.exchangeVoucherIds = new ArrayList<>();
        this.creditCardIds = new ArrayList<>();
        this.creditCardPayments = new ArrayList<>();
    }
}
