package org.fatec.esportiva.mapper;

import lombok.experimental.UtilityClass;
import org.fatec.esportiva.entity.Notification;
import org.fatec.esportiva.dto.response.NotificationResponseDto;

@UtilityClass
public class NotificationMapper {
    public NotificationResponseDto toNotificationResponseDto(Notification notification){
        return new NotificationResponseDto(notification.getId(), notification.getMessage(), notification.getCreatedAt(), notification.getIsViewed());
    }
}
