package org.fatec.esportiva.utils;

import org.fatec.esportiva.entity.Order;
import org.fatec.esportiva.entity.Transaction;
import org.fatec.esportiva.entity.enums.OrderStatus;
import org.fatec.esportiva.entity.product.Product;

public class OrderFactory {
    public static Order defaultOrder(Transaction transaction, Product product){
        return Order.builder()
                .status(OrderStatus.EM_PROCESSAMENTO)
                .transaction(transaction)
                .product(product)
                .quantity(5)
                .build();
    }
}
