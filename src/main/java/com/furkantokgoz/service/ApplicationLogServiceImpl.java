package com.furkantokgoz.service;


import com.furkantokgoz.config.ClientAddressResolver;
import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.entity.ApplicationLogEntity;
import com.furkantokgoz.exception.ApplicationLogNotFoundException;
import com.furkantokgoz.mapper.ApplicationLogMapper;
import com.furkantokgoz.repository.ApplicationLogRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationLogServiceImpl implements IApplicationLogService{


    private final ClientAddressResolver clientAddressResolver;
    private ApplicationLogRepository applicationLogRepository;

    @Autowired
    public ApplicationLogServiceImpl(ApplicationLogRepository applicationLogRepository, ClientAddressResolver clientAddressResolver) {
        this.applicationLogRepository = applicationLogRepository;
        this.clientAddressResolver = clientAddressResolver;
    }

    @Override
    public List<ApplicationLogDto> getApplicationLogs() {
        return applicationLogRepository.findAll().stream()
                .map( ApplicationLogMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationLogDto create(ApplicationLogDto applicationLogDto) {
        applicationLogDto.setTimestamp(LocalDateTime.now().withNano(0).atZone(ZoneId.systemDefault()));
        applicationLogDto.setClientIp(clientAddressResolver.Resolve());
        return ApplicationLogMapper.toDto(applicationLogRepository.save(ApplicationLogMapper.toEntity(applicationLogDto)));
    }
    @Override
    public ApplicationLogDto findById(long id) {
        return ApplicationLogMapper.toDto(applicationLogRepository.findById(id).orElseThrow(ApplicationLogNotFoundException::new));
    }
    @Override
    public List<ApplicationLogDto> findLogTypes(LogType logType){
        return applicationLogRepository.findByLogType(logType).stream()
                .map(ApplicationLogMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ApplicationLogDto> findLoggerNames(String loggerName){
        List<ApplicationLogDto> applicationLogDtos = new ArrayList<>();
        applicationLogRepository.findByLoggerName(loggerName).stream()
                .map(ApplicationLogMapper::toDto)
                .forEach(applicationLogDtos::add);
        if(applicationLogDtos.isEmpty() || applicationLogDtos == null){
            throw new ApplicationLogNotFoundException();
        }
        return applicationLogDtos;
    }
    @Override
    public List<ApplicationLogDto> findByUserId(String userId){
        List<ApplicationLogDto> logDtos = applicationLogRepository.findByUserName(userId)
                .stream().map(ApplicationLogMapper::toDto)
                .collect(Collectors.toList());
        if(logDtos.isEmpty() || logDtos == null){
            throw new ApplicationLogNotFoundException();
        }
        return logDtos;
    }

    @Override
    public ApplicationLogDto deleteById(long id) {
        ApplicationLogEntity entity = applicationLogRepository.findById(id).orElseThrow(ApplicationLogNotFoundException::new);
        applicationLogRepository.delete(entity);
        return ApplicationLogMapper.toDto(entity);
    }
    @Override
    public ApplicationLogDto update(long id,ApplicationLogDto applicationLogDto) {
        ApplicationLogEntity applicationLogEntity = applicationLogRepository.findById(id).orElseThrow(ApplicationLogNotFoundException::new);
        if(applicationLogDto.getLogType() != null) {
            applicationLogEntity.setLogType(applicationLogDto.getLogType());
        }
        if(applicationLogDto.getMessage() != null) {
            applicationLogEntity.setMessage(applicationLogDto.getMessage());
        }
        if(applicationLogDto.getTimestamp() != null) {
            applicationLogEntity.setTimestamp(applicationLogDto.getTimestamp());
        }
        if(applicationLogDto.getClientIp() != null) {
            applicationLogEntity.setClientIp(applicationLogDto.getClientIp());
        }
        if(applicationLogDto.getLoggerName() != null) {
            applicationLogEntity.setLoggerName(applicationLogDto.getLoggerName());
        }
        if(applicationLogDto.getUserName() != null) {
            applicationLogEntity.setUserName(applicationLogDto.getUserName());
        }
        applicationLogRepository.save(applicationLogEntity);
        return ApplicationLogMapper.toDto(applicationLogEntity);
    }
}

