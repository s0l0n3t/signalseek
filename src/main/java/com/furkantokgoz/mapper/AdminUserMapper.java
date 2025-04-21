package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.entity.AdminUserEntity;

public class AdminUserMapper {
    public static AdminUserDto toDto(AdminUserEntity adminUserEntity) {
        return AdminUserDto.builder().id(adminUserEntity.getId())
                .username(adminUserEntity.getUsername())
                .password(adminUserEntity.getPassword())
                .email(adminUserEntity.getEmail())
                .build();
    }

    public static AdminUserEntity toEntity(AdminUserDto adminUserDto) {
        return AdminUserEntity.builder().id(adminUserDto.getId())
                .email(adminUserDto.getEmail())
                .username(adminUserDto.getUsername())
                .password(adminUserDto.getPassword())
                .build();
    }
}


/* This using can usable for bigger projects. Because there is dependency.
// mapper/AdminUserMapper.java
@Mapper(componentModel = "AdminUser")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDto);
}
 */