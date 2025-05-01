package com.furkantokgoz.repository;

import com.furkantokgoz.dto.ApplicationLogDto;
import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.entity.ApplicationLogEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Hidden
@Repository
public interface ApplicationLogRepository extends JpaRepository<ApplicationLogEntity,Long> {
    public Optional<ApplicationLogEntity> findById(long applicationId);
    public List<ApplicationLogEntity> findByLogType(LogType logType);
    public List<ApplicationLogEntity> findByLoggerName(String loggerName);
    public List<ApplicationLogEntity> findByUserName(String userName);
    public ApplicationLogEntity deleteById(long applicationId);
}
