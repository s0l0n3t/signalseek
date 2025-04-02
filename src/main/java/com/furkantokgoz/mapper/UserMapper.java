package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;

public class UserMapper {
    public static UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id((int)userEntity.getId())
                .userKey(userEntity.getUserKey() != null ? userEntity.getUserKey() : "test1")
                .ipAddress(userEntity.getIpAddress() != null ? userEntity.getIpAddress() : "128.0.0.1")
                .latitude(userEntity.getLatitude() != null ? userEntity.getLatitude() : 0.0)
                .longitude(userEntity.getLongitude() != null ? userEntity.getLongitude() : 0.0)
                .roomKey(userEntity.getRoomKey() != null ? userEntity.getRoomKey() : "none")
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