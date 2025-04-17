package org.fatec.esportiva.mapper;

import org.fatec.esportiva.entity.Log;
import org.fatec.esportiva.dto.request.LogDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMapper {
    public Log toLog(LogDto logDto) {
        return Log.builder()
                .user(logDto.getUser())
                .timestamp(logDto.getTimestamp())
                .operation(logDto.getOperation())
                .operationContent(logDto.getOperationContent())
                .build();
    }

    public LogDto toLogDto(Log log) {
        return LogDto.builder()
                .id(log.getId())
                .user(log.getUser())
                .timestamp(log.getTimestamp())
                .operation(log.getOperation())
                .operationContent(log.getOperationContent())
                .build();
    }
}
