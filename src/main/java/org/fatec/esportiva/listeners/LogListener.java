package org.fatec.esportiva.listeners;

import java.time.LocalDateTime;

import org.fatec.esportiva.entity.Log;
import org.fatec.esportiva.repository.LogRepository;
import org.fatec.esportiva.service.AuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

// https://stackoverflow.com/questions/66304627/spring-boot-entitylistener-the-dependencies-of-some-of-the-beans-in-the-applica
// Obs: Colocar isso nas entidades que quer interceptar: @EntityListeners(LogListener.class)
// Obs: Se usar o PrePersist e PreUpdate, ele gera uns 5 logs para cada commit, devido a cascata
@Component
public class LogListener {
    private final LogRepository logRepository;
    private AuthService authService;

    // Para evitar o problema de importação circular, o @Lazy é adicionado na
    // instanciação do objeto de Listener
    public LogListener(@Lazy LogRepository logRepository, @Lazy AuthService authService) {
        this.logRepository = logRepository;
        this.authService = authService;
    }

    // Detecta qualquer commit depois de acontecer do tipo INSERT
    @PostPersist
    public void afterInsert(Object entity) throws Exception {
        Log log = new Log();
        log.setUser(authService.getAuthenticatedUser().getUsername());
        log.setTimestamp(LocalDateTime.now());
        log.setOperation("INSERT");
        log.setOperationContent(entity.toString());

        saveLog(log);
    }

    // Detecta qualquer commit depois de acontecer do tipo UPDATE
    @PostUpdate
    public void afterUpdate(Object entity) throws Exception {
        Log log = new Log();
        log.setUser(authService.getAuthenticatedUser().getUsername());
        log.setTimestamp(LocalDateTime.now());
        log.setOperation("UPDATE");
        log.setOperationContent(entity.toString());

        saveLog(log);
    }

    // @PreRemove // Detecta qualquer commit antes de acontecer do tipo DELETE

    // Salva na tabela Log. A solução final é usar o logRepository diretamente
    private void saveLog(Log log) {
        logRepository.save(log);
    }
}