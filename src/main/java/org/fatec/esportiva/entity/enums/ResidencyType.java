package org.fatec.esportiva.entity.enums;

public enum ResidencyType {
    CASA("Casa"), APARTAMENTO("Apartamento"), OUTROS("Outros");

    private final String displayName;

    ResidencyType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
