package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Notification;
import org.fatec.esportiva.response.NotificationResponseDto;

@UtilityClass
public class NotificationMapper {
    public NotificationResponseDto toNotificationResponseDto(Notification notification){
        return new NotificationResponseDto(notification.getMessage(), notification.getCreatedAt());
    }
}
