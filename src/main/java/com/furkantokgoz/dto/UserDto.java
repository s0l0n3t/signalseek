package com.furkantokgoz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    private String userKey;
    private String ipAddress; //Max 15 char
    private Double latitude; // 10,8
    private Double longitude; // 11,8
    private String roomKey;
}
