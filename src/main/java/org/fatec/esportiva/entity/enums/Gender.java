package org.fatec.esportiva.entity.enums;

public enum Gender {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    NAO_BINARIO("Não binário"),
    PREFERE_NAO_DIZER("Prefiro não dizer");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public  String getDisplayName(){
        return displayName;
    }
}
