package org.fatec.esportiva.entity.enums;

public enum CreditCardBrand {
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMERICAN_EXPRESS("American Express"),
    DISCOVER("Discover");

    private final String displayName;

    CreditCardBrand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
