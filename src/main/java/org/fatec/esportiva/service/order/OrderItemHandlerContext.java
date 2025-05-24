package org.fatec.esportiva.service.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.fatec.esportiva.service.ExchangeVoucherService;
import org.fatec.esportiva.service.NotificationService;

@AllArgsConstructor
@Getter
@Setter
public class OrderItemHandlerContext {
    private Boolean isApproved;
    private Boolean isReturnStock;
    private NotificationService notificationService;
    private ExchangeVoucherService exchangeVoucherService;
}
