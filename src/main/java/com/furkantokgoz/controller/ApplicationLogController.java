package com.furkantokgoz.controller;

import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.entity.ApplicationLogEntity;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
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
public class ApplicationLogController {

    @Autowired
    private ApplicationLogServiceImpl applicationLogServiceImpl;


    @PostMapping("/create")
    public ResponseEntity createLog(@RequestBody ApplicationLogDto applicationLogDto) {
        applicationLogServiceImpl.create(applicationLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationLogDto);
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteLog(@RequestBody ApplicationLogDto applicationLogDto) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.deleteById(applicationLogDto.getId()));
    }
    @PutMapping(value = "/update",params = "id")
    public ResponseEntity updateLog(@RequestBody ApplicationLogDto applicationLogDto,@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.update(id, applicationLogDto));
    }
    @GetMapping(value = "/find",params = "id")
    public ResponseEntity findById(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findById(id));
    }
    @GetMapping("/find")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.getApplicationLogs());
    }
    @GetMapping(value = "/find",params = "loggername")
    public ResponseEntity findByLoggerName(@RequestParam String loggerName) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findLoggerNames(loggerName));
    }
    @GetMapping(value = "/find",params = "logType")
    public ResponseEntity findByLogType(@RequestParam LogType logType) {
        return ResponseEntity.status(HttpStatus.OK).body(applicationLogServiceImpl.findLogTypes(logType));
    }
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