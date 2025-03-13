package org.fatec.esportiva.entity.enums;

public enum UserStatus {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String displayName;

    UserStatus(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
