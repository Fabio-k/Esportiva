package org.fatec.esportiva.exception;

import lombok.Getter;

@Getter
public class CheckoutException extends RuntimeException {
    private final String redirectPage;
    public CheckoutException(String message, String redirectPage) {
        super(message);
        this.redirectPage = redirectPage;
    }
}
