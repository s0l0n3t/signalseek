package com.furkantokgoz.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class ClientAddressResolver {
    private HttpServletRequest request;
    public ClientAddressResolver(HttpServletRequest request) {
        this.request = request;
    }

    public String Resolve(){
        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        return Stream.of(xRealIp, xForwardedFor, remoteAddr)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}
