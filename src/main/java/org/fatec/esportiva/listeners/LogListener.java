package org.fatec.esportiva.listeners;

import java.time.LocalDateTime;

import org.fatec.esportiva.entity.Log;

import org.fatec.esportiva.service.AuthService;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PrePersist;
//import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.transaction.Transactional;

// Colocar isso nas entidades que quer interceptar: @EntityListeners(LogListener.class)

@Component
public class LogListener {

    // Injetará o EntityManager configurado automaticamente no contexto do Spring
    @PersistenceContext
    private EntityManager entityManager;

    private AuthService authService;

    @PrePersist
    public void beforeInsert(Object entity) throws Exception {
        Log log = new Log();
        // log.setUser(authService.getAuthenticatedUser().getUsername());
        log.setUser("TESTE");
        log.setOperation("INSERT");
        // log.setOperationContent(entity.toString());
        // log.setTimestamp(LocalDateTime.now());
        log.setOperationContent("CONTEUDO");
        log.setTimestamp(LocalDateTime.now());

        saveLog(log);
    }

    @PreUpdate
    public void beforeUpdate(Object entity) throws Exception {
        Log log = new Log();
        // log.setUser(authService.getAuthenticatedUser().getUsername());
        log.setUser("TESTE");
        log.setOperation("UPDATE");
        // log.setOperationContent(entity.toString());
        // log.setTimestamp(LocalDateTime.now());
        log.setOperationContent("CONTEUDO");
        log.setTimestamp(LocalDateTime.now());

        saveLog(log);
    }

    // @PreRemove
    // public void beforeDelete(Object entity) {
    // Log log = new Log();
    // log.setUser();
    // log.setOperation("DELETE");
    // log.setOperationContent(entity.toString());
    // log.setTimestamp(LocalDateTime.now());

    // saveLog(log);
    // }

    // Use EntityManager para salvar o log no banco de dados
    // @ Transactional -> O Jakarta gerencia a transação automaticamente, sem
    // necessidade de chamar manualmente begin() ou commit()
    @Transactional
    private void saveLog(Log log) {
        entityManager.persist(log);
    }
}