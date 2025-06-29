package org.fatec.esportiva.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionRequest {
    private Long transactionId;
    private Long orderId;
    @NotNull
    private Boolean isApproved;
    private Boolean isReturnToStock;
}
