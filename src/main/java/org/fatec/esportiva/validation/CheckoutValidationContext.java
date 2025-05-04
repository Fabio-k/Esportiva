package org.fatec.esportiva.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.entity.session.CheckoutSession;

@AllArgsConstructor
@Getter
@Setter
public class CheckoutValidationContext {
    private CheckoutSession checkoutSession;
    private Long clientId;
    private String redirectPage;
}
