package com.furkantokgoz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class LoggerConfigBean {
    public static final String UNAUTHENTICATED = "User not authorized";

    public static String errorRequestLog(String inputKey, Authentication auth) {
        return "User "+ auth.getName()+" unauthorized request to "+ inputKey;
    }//continue
    public static String infoFoundLog(String inputKey, Authentication auth) {
        return auth.getName()+" searched "+ inputKey;
    }
    public static String errorAuthLog(String username) {
        return "tried to access "+username;
    }
    public static String loginAuthLog(String username) {
        return username + " logged in";
    }
    public static String infoMapLog(String userKey, String latitude, String longitude) {
        return "User "+ userKey+" added location "+ latitude+":"+longitude;
    }
}