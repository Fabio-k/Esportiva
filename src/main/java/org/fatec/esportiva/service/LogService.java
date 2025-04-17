package org.fatec.esportiva.service;

import java.util.List;

import org.fatec.esportiva.mapper.LogMapper;
import org.fatec.esportiva.repository.LogRepository;
import org.fatec.esportiva.dto.request.LogDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public List<LogDto> getLogs() {

        List<LogDto> clients = logRepository
                .findAll().stream()
                .map(LogMapper::toLogDto).toList();
        return clients;
    }
}
