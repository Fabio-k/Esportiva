package org.fatec.esportiva.service;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Notification;
import org.fatec.esportiva.mapper.NotificationMapper;
import org.fatec.esportiva.repository.NotificationRepository;
import org.fatec.esportiva.response.NotificationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ClientService clientService;

    public void createNotification(Client client, String message){
        Notification notification = new Notification(client, message);
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDto> getNotifications() {
        Client client = clientService.getAuthenticatedClient();
        List<Notification> notifications = notificationRepository.findAllByClientOrderByCreatedAtDesc(client);
        return notifications.stream().map(NotificationMapper::toNotificationResponseDto).toList();
    }
}
