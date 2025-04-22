package com.furkantokgoz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private int id;
    private String roomKey;
    private List<String> userKeys;
    private Integer userCount;
    private Collection<? extends GrantedAuthority> authorities;
}
