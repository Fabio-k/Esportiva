package org.fatec.esportiva.entity.enums;

public enum PhoneType {
    CELULAR("Celular"),
    TELEFONE("Telefone"),
    TELEFONE_VoIP("Telefone VoIP");

    private final String displayName;

    PhoneType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
