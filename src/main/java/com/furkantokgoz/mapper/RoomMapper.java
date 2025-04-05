package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.entity.RoomEntity;

public class RoomMapper {
    public static RoomDto toDto(RoomEntity roomEntity) {
        return RoomDto.builder()
                .id((int)roomEntity.getId())
                .roomKey(roomEntity.getRoomKey())
                .build();
    }
    public static RoomEntity toEntity(RoomDto roomDto) {
        return RoomEntity.builder()
                .id(roomDto.getId())
                .roomKey(roomDto.getRoomKey())
                .build();
    }
}
