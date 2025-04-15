package org.fatec.esportiva.response;

import java.time.LocalDateTime;

public record NotificationResponseDto(Long id, String message, LocalDateTime createdAt, Boolean isViewed) {
}
