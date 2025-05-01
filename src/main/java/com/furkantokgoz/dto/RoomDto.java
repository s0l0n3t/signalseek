package com.furkantokgoz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "room id",example = "1", required = true)
    private int id;
    @Schema(name = "roomkey as Unique")
    private String roomKey;
    @Schema(name = "userkeys include a room")
    private List<String> userKeys;
    @Schema(name = "user count included a room")
    private Integer userCount;
    @Schema(name = "room perm")
    private Collection<? extends GrantedAuthority> authorities;
}
