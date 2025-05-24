package org.fatec.esportiva.service.transaction;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.service.ExchangeVoucherService;
import org.fatec.esportiva.service.order.OrderService;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TransactionContext {
    private final OrderService orderService;
    private final ExchangeVoucherService exchangeVoucherService;

    public void propagateStatusToOrder(Transaction transaction, boolean approve) {
        // Propaga o status para os pedidos
        for (Order order : transaction.getOrders()) {
            orderService.changeState(order.getId(), approve, false);
        }
    }

    public void refundSingleVoucher(Transaction transaction) {
        Client client = transaction.getClient();
        BigDecimal voucherValue = transaction.getTotalCost();
        if (voucherValue.compareTo(BigDecimal.ZERO) < 1)
            return;
        // Cria um novo cupom
        exchangeVoucherService.createExchangeVoucher(client, voucherValue);
    }

}
