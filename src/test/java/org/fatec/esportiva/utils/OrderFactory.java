package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;

public class OrderFactory {
    public static Order defaultOrder(Transaction transaction){
        return Order.builder()
                .status(OrderStatus.EM_PROCESSAMENTO)
                .transaction(transaction)
                .product(ProductFactory.defaultProduct())
                .quantity(5)
                .build();
    }

    public static Order deliveredOrder(Transaction transaction){
        return Order.builder()
                .status(OrderStatus.ENTREGUE)
                .transaction(transaction)
                .product(ProductFactory.defaultProduct())
                .quantity(5)
                .build();

    }

}
