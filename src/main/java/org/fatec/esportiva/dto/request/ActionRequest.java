package org.fatec.esportiva.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionRequest {
    private Long transactionId;
    private Long orderId;
    private Boolean isApproved;
    private Boolean isReturnToStock;
}
