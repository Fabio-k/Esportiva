package org.fatec.esportiva.service;

import jakarta.transaction.Transactional;
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
        if(client == null) throw new IllegalArgumentException("Cliente não pode ser nulo");
        if(message == null || message.trim().isEmpty()) throw new IllegalArgumentException("Mensagem não pode estar vazia");
        Notification notification = new Notification(client, message);
        notificationRepository.save(notification);
    }

    public List<NotificationResponseDto> getNotifications() {
        Client client = clientService.getAuthenticatedClient();
        List<Notification> notifications = notificationRepository.findAllByClientOrderByCreatedAtDesc(client);
        return notifications.stream().map(NotificationMapper::toNotificationResponseDto).toList();
    }

    @Transactional
    public void markAsViewed(List<Long> ids) {
        if(ids == null || ids.isEmpty()) return;
        Client client = clientService.getAuthenticatedClient();
        List<Notification> notifications = notificationRepository.findAllByIdInAndClientId(ids, client.getId());

        for(Notification notification : notifications){
            notification.setIsViewed(true);
        }

        notificationRepository.saveAll(notifications);
    }
}
