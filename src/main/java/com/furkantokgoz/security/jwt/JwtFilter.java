package com.furkantokgoz.security.jwt;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.exception.TokenNotFoundException;
import com.furkantokgoz.service.AdminUserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private AdminUserServiceImpl adminUserServiceImpl;
    private JwtUtil jwtUtil;

    public JwtFilter(AdminUserServiceImpl adminUserServiceImpl, JwtUtil jwtUtil) {
        this.adminUserServiceImpl = adminUserServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        try{
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AdminUserDto adminUserDto = adminUserServiceImpl.getAdminUserByUsername(username);
                if (jwtUtil.validateToken(jwt, adminUserDto) && adminUserDto != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(adminUserDto, null, adminUserDto.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (TokenNotFoundException e) {
            throw new TokenNotFoundException(e.getMessage());
        }catch (NullPointerException e) {
            throw new TokenNotFoundException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }


}
