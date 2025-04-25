package com.furkantokgoz.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class LoggerConfigBean {

    public static String errorLog(String inputKey, Authentication auth) {
        return "User "+ auth.getName()+" unauthorized request to "+ inputKey;
    }//continue
}
