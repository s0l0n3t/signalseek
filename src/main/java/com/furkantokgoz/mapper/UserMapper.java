package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.repository.RoomRepository;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id((int)userEntity.getId())
                .userKey(userEntity.getUserKey())
                .ipAddress(userEntity.getIpAddress())
                .latitude(userEntity.getLatitude())
                .longitude(userEntity.getLongitude())
                .roomKey(userEntity.getRoom().getRoomKey())
                .geoLocations(userEntity.getGeolocations().stream()
                        .map(GeoLocationMapper::toDto)
                        .collect(Collectors.toList()))
                .build();

    }
    public static UserEntity toEntity(UserDto userDto, RoomRepository roomRepository) {
        UserEntity userEntity = UserEntity.builder()
                .id(userDto.getId())
                .userKey(userDto.getUserKey())
                .ipAddress(userDto.getIpAddress())
                .latitude(userDto.getLatitude())
                .longitude(userDto.getLongitude())
                .geolocations(userDto.getGeoLocations().stream()
                        .map(GeoLocationMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
        if(userDto.getRoomKey() != null) {
            RoomEntity roomEntity = roomRepository.findByRoomKey(userDto.getRoomKey())
                    .orElseThrow(() -> new UserNotFoundException(userDto.getRoomKey()));
            userEntity.setRoom(roomEntity);
            //RoomNotFound Exception
        }
        return userEntity;
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