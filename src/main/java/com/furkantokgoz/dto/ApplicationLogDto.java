package com.furkantokgoz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "Admin user id",example = "1", required = true)
    private long id;
    @Schema(name = "Logtype for log",example = "ERROR, WARN, INFO, DEBUG, TRACE")
    private LogType logType;
    @Schema(name = "log message")
    private String message;
    @Schema(name = "log service classname",example = "AdminUserServiceImpl")
    private String loggerName;
    @Schema(name = "log identify key")
    private String userName;
    @Schema(name = "log time")
    private ZonedDateTime timestamp;
    @Schema(name = "log ip address")
    private String clientIp;
}
