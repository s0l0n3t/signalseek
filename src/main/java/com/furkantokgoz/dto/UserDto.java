package com.furkantokgoz.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    @NonNull
    private String userKey;
    @NonNull
    private String ipAddress; //Max 15 char
    private Double latitude; // 10,8
    private Double longitude; // 11,8
    private String roomKey;
}
