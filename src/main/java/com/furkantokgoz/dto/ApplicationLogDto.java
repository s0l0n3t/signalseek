package com.furkantokgoz.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationLogDto {
    private long id;
    private LogType logType;
    private String message;
    private String loggerName;
    private String userName;
    private ZonedDateTime timestamp;
    private String clientIp;
}
