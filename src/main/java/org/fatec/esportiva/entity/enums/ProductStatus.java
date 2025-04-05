package org.fatec.esportiva.entity.enums;

public enum ProductStatus {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    FORA_DE_MERCADO("Fora de mercado");

    private final String displayName;

    ProductStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}