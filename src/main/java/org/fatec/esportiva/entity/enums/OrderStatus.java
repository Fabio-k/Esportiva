package org.fatec.esportiva.entity.enums;

import java.util.List;

public enum OrderStatus {
    CARRINHO_COMPRAS("Carrinho de compras"),
    EM_PROCESSAMENTO("Em processamento"),
    EM_TRANSITO("Em trânsito"),
    ENTREGUE("Entregue"),
    EM_TROCA("Em troca"),
    TROCADO("Trocado"),
    TROCA_FINALIZADA("Troca finalizada"),
    COMPRA_CANCELADA("Compra cancelada"),
    TROCA_RECUSADA("Troca recusada");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public  String getDisplayName(){
        return displayName;
    }

    public Boolean isBeingTraded(){
        return this == EM_TROCA || this == TROCADO || this == TROCA_FINALIZADA || this == TROCA_RECUSADA;
    }

    public boolean isInDeliveryProcess() {
        return this == ENTREGUE || this == EM_TRANSITO || this == EM_PROCESSAMENTO;
    }

    public static List<OrderStatus> getSalesReportStatus(){
        return List.of(ENTREGUE, TROCA_RECUSADA);
    }
}
