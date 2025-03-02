package org.fatec.esportiva.repository;

import org.fatec.esportiva.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String> {
}
