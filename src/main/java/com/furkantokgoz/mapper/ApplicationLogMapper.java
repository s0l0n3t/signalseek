package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.entity.ApplicationLogEntity;

public class ApplicationLogMapper {

    public static ApplicationLogDto toDto(ApplicationLogEntity applicationLogEntity) {
        return ApplicationLogDto.builder()
                .id(applicationLogEntity.getId())
                .logType(applicationLogEntity.getLogType())
                .loggerName(applicationLogEntity.getLoggerName())
                .message(applicationLogEntity.getMessage())
                .timestamp(applicationLogEntity.getTimestamp())
                .userName(applicationLogEntity.getUserName())
                .clientIp(applicationLogEntity.getClientIp())
                .build();
    }

    public static ApplicationLogEntity toEntity(ApplicationLogDto applicationLogDto) {
        return ApplicationLogEntity.builder()
                .id(applicationLogDto.getId())
                .logType(applicationLogDto.getLogType())
                .loggerName(applicationLogDto.getLoggerName())
                .message(applicationLogDto.getMessage())
                .userName(applicationLogDto.getUserName())
                .timestamp(applicationLogDto.getTimestamp())
                .clientIp(applicationLogDto.getClientIp())
                .build();
    }
}

//
//private long id;
//private LogType logType;
//private String message;
//private String loggerName;
//private String userId;
//private ZonedDateTime timestamp;
//private String clientIp;