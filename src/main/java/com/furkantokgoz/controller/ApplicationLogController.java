package com.furkantokgoz.controller;

import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.entity.ApplicationLogEntity;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import lombok.extern.java.Log;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/admin/log")
@Tag(name="Application Log API")
public class ApplicationLogController {

    @Autowired
    private ApplicationLogServiceImpl applicationLogServiceImpl;

    @Operation(summary = "log creation",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You created a log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @PostMapping("/create")
    public ResponseEntity createLog(@RequestBody ApplicationLogDto applicationLogDto) {
        applicationLogServiceImpl.create(applicationLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationLogDto);
    }
    @Operation(summary = "log delete",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "log deleted"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deleteLog(@RequestBody ApplicationLogDto applicationLogDto) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.deleteById(applicationLogDto.getId()));
    }
    @Operation(summary = "log update",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated a log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @PutMapping(value = "/update",params = "id")
    public ResponseEntity updateLog(@RequestBody ApplicationLogDto applicationLogDto,@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.update(id, applicationLogDto));
    }
    @Operation(summary = "log find as id",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping(value = "/find",params = "id")
    public ResponseEntity findById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findById(id));
    }
    @Operation(summary = "log find all",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping("/find")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.getApplicationLogs());
    }
    @Operation(summary = "log find by logger service name",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping(value = "/find",params = "loggername")
    public ResponseEntity findByLoggerName(@RequestParam String loggerName) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findLoggerNames(loggerName));
    }
    @Operation(summary = "log find by type",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping(value = "/find",params = "logType")
    public ResponseEntity findByLogType(@RequestParam LogType logType) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findLogTypes(logType));
    }
    @Operation(summary = "log find by key",description = "you can reach if you are an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found log"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping(value = "/find",params = "userid")
    public ResponseEntity findByUserId(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findByUserId(userId));
    }
}

//
//@Column(nullable = false)
//private LogType logType;
//@Column(nullable = false)
//private String message;
//@Column(nullable = false)
//private String loggerName;
//private String userId;
//@Column(nullable = false)
//private ZonedDateTime timestamp;