package com.furkantokgoz.security.jwt;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.exception.TokenNotFoundException;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.repository.AdminUserRepository;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.repository.UserRepository;
import com.furkantokgoz.service.AdminUserServiceImpl;
import com.furkantokgoz.service.RoomServiceImpl;
import com.furkantokgoz.service.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserServiceImpl userServiceImpl;
    private final RoomServiceImpl roomServiceImpl;
    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;
    private final RoomRepository roomRepository;
    private AdminUserServiceImpl adminUserServiceImpl;
    private JwtUtil jwtUtil;

    public JwtFilter(AdminUserServiceImpl adminUserServiceImpl, JwtUtil jwtUtil, UserServiceImpl userServiceImpl, RoomServiceImpl roomServiceImpl, UserRepository userRepository, AdminUserRepository adminUserRepository, RoomRepository roomRepository) {
        this.adminUserServiceImpl = adminUserServiceImpl;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
        this.roomServiceImpl = roomServiceImpl;
        this.userRepository = userRepository;
        this.adminUserRepository = adminUserRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = null;
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
        else{
            filterChain.doFilter(request, response);
            return;
        }
        try{
            System.out.println(jwtUtil.extractAllClaims(jwt));//
            if (jwtUtil.validateToken(jwt)) {
                String hash = jwtUtil.extractUsername(jwt);
                var authorities = jwtUtil.extractAuthorities(jwt);
                if(adminUserRepository.existsByUsername(hash) != false || roomRepository.existsByRoomKey(hash) != false || userRepository.existsByUserKey(hash) != false) {
                    var auth = new UsernamePasswordAuthenticationToken(hash, null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (TokenNotFoundException e) {
            throw new TokenNotFoundException(e.getMessage());
        }catch (NullPointerException e) {
            throw new TokenNotFoundException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public boolean isUserAuthorized(Authentication authentication, String userKey) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER") )) {
            if(userKey.equals(authentication.getName())){
                return true;
            }
        }
        return false;
    }
}