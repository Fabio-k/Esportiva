package org.fatec.esportiva.response;

import java.time.LocalDateTime;

public record NotificationResponseDto(String message, LocalDateTime createdAt) {
}
