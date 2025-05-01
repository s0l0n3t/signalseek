package com.furkantokgoz.entity;

import com.furkantokgoz.dto.LogType;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ApplicationLog")
@Hidden
public class ApplicationLogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogType logType;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String loggerName;
    private String userName;
    @Column(nullable = false)
    private ZonedDateTime timestamp;
    private String clientIp;
}
