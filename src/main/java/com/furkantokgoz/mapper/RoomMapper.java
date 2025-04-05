package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.exception.RoomNotFoundException;
import com.furkantokgoz.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
//add UserRepository Object
public class RoomMapper {
    public static RoomDto toDto(RoomEntity roomEntity, UserRepository userRepository) {
        List<UserEntity> userEntityListByRoomKey = userRepository.findByRoom_RoomKey(roomEntity.getRoomKey()).orElseThrow(() -> new RoomNotFoundException(roomEntity.getRoomKey()));
        List<String> userKeyStringList = new ArrayList<>();
        userEntityListByRoomKey.forEach( u -> userKeyStringList.add(u.getUserKey()));
        return RoomDto.builder()
                .id((int)roomEntity.getId())
                .roomKey(roomEntity.getRoomKey())
                .userCount(userEntityListByRoomKey.size())
                .userKeys(userKeyStringList)
                .build();
    }
    public static RoomEntity toEntity(RoomDto roomDto) {
        return RoomEntity.builder()
                .id(roomDto.getId())
                .roomKey(roomDto.getRoomKey())
                .build();
    }
}
