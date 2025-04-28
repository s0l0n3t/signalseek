package com.furkantokgoz.service;

import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.entity.ApplicationLogEntity;

import java.util.List;


public interface IApplicationLogService {
    public List<ApplicationLogDto> getApplicationLogs();
    public ApplicationLogDto findById(long id);
    public ApplicationLogDto create(ApplicationLogDto applicationLogDto);
    public List<ApplicationLogDto> findLogTypes(LogType logType);
    public List<ApplicationLogDto> findLoggerNames(String loggerName);
    public List<ApplicationLogDto> findByUserId(String userId);
    public ApplicationLogDto deleteById(long id);
    public ApplicationLogDto update(long id,ApplicationLogDto applicationLogDto);
}
//
//public List<ApplicationLogEntity> findBylogType(LogType logType);
//public List<ApplicationLogEntity> findByLoggerName(String loggerName);
//public List<ApplicationLogEntity> findByUserId(String userId);

