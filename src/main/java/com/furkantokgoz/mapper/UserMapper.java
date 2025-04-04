package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;

public class UserMapper {
    public static UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id((int)userEntity.getId())
                .userKey(userEntity.getUserKey())
                .ipAddress(userEntity.getIpAddress())
                .latitude(userEntity.getLatitude())
                .longitude(userEntity.getLongitude())
                .roomKey(userEntity.getRoomKey())
                .build();

    }
    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .userKey(userDto.getUserKey())
                .ipAddress(userDto.getIpAddress())
                .latitude(userDto.getLatitude())
                .longitude(userDto.getLongitude())
                .roomKey(userDto.getRoomKey())
                .build();
    }
}

/* This using can usable for bigger projects. Because there is dependency.
// mapper/UserMapper.java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDto);
}
 */