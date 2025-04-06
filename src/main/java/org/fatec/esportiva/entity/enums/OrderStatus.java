package org.fatec.esportiva.entity.enums;

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
}
