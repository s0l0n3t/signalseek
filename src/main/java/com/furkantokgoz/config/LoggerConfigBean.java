package com.furkantokgoz.config;

import com.furkantokgoz.dto.LogType;
import com.furkantokgoz.dto.Roles;
import com.furkantokgoz.entity.ApplicationLogEntity;
import com.furkantokgoz.mapper.ApplicationLogMapper;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class LoggerConfigBean {
    public static final String UNAUTHENTICATED = "User not authorized";


    public static String errorRequestLog(String inputKey, Authentication auth,ApplicationLogServiceImpl service,String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(auth.getName());
        log.setMessage(auth.getName() + " unauthorized request to " + inputKey);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.ERROR);
        service.create(ApplicationLogMapper.toDto(log));
        return "User "+ auth.getName()+" unauthorized request to "+ inputKey;
    }
    public static String infoFoundLog(String inputKey, Authentication auth,ApplicationLogServiceImpl service,String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(auth.getName());
        log.setMessage(auth.getName() + " searched " + inputKey);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return auth.getName()+" searched "+ inputKey;
    }
    public static String errorAuthLog(String username,ApplicationLogServiceImpl service,String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName("Not Authorized");
        log.setMessage("Tried to access " + username);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.WARN);
        service.create(ApplicationLogMapper.toDto(log));
        return "tried to access "+username;
    }
    public static String loginAuthLog(String username,ApplicationLogServiceImpl service,String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName("Not Authorized");
        log.setMessage("Logged in to " + username);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return username + " logged in";
    }
    public static String infoMapLog(String userKey, String latitude, String longitude, String serviceClass, ApplicationLogServiceImpl service) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(userKey);
        log.setMessage(userKey +" added location " + latitude + longitude);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return "User "+ userKey+" added location "+ latitude+":"+longitude;
    }
    public static String userCrated(Roles role,String username, ApplicationLogServiceImpl service, String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(username);
        log.setMessage(username + " created ");
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return role.name()+" "+ username + " created ";
    }
    public static String userDeleted(Roles role,String username, ApplicationLogServiceImpl service, String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(username);
        log.setMessage(username + " deleted ");
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return role.name()+" "+ username + " deleted ";
    }
    public static String userAllCalled(Roles role,String username, ApplicationLogServiceImpl service, String serviceClass) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(username);
        log.setMessage(username + " all "+role.name()+ " called");
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return role.name()+" "+ username + " all called ";
    }
    public static String userUpdated(String username, ApplicationLogServiceImpl service, String serviceClass,Authentication auth) {
        ApplicationLogEntity log = new ApplicationLogEntity();
        log.setUserName(auth.getName());
        log.setMessage(auth.getName() + " updated to "+ username);
        log.setLoggerName(serviceClass);
        log.setLogType(LogType.INFO);
        service.create(ApplicationLogMapper.toDto(log));
        return auth.getAuthorities().toString()+ " "+ auth.getName() + " updated to "+ username;
    }
}