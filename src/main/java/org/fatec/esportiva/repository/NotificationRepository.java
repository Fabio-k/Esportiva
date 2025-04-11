package org.fatec.esportiva.repository;

import org.fatec.esportiva.entity.Client;
import org.fatec.esportiva.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByClientOrderByCreatedAtDesc(Client client);
}
