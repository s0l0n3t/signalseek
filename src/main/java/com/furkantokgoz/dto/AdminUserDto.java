package com.furkantokgoz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserDto {
    private long id;
    private String username;
    private String password;
    private String email;

    private Collection<? extends GrantedAuthority> authorities; //no authority
}
