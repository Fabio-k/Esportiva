package org.fatec.esportiva.entity.enums;

public enum StreetType {
    RUA("Rua"), AVENIDA("Avenida"), PRACA("Praça"), VIELA("Viela"), RODOVIA("Rodovia"), OUTROS("Outros");

    private final String displayName;

    StreetType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
