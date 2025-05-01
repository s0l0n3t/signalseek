package com.furkantokgoz.dto;

import com.furkantokgoz.entity.GeoLocationEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Schema(name = "user id",example = "1", required = true)
    private int id;
    @NonNull
    @Schema(name = "userkey as Unique")
    private String userKey;
    @NonNull
    @Schema(name = "user ipaddress")
    private String ipAddress; //Max 15 char
    @Schema(name = "geolocation latitude")
    private Double latitude; // 10,8
    @Schema(name = "geolocation longitude")
    private Double longitude; // 11,8
    @Schema(name = "roomkey include this user")
    private String roomKey;
    //Auth collection
    @Schema(name = "user perms")
    private Collection<? extends GrantedAuthority> authorities; //no authority
    private List<GeoLocationDto> geoLocations;
}
