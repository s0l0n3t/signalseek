package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;

import java.util.List;

public interface IRoomService {
    public RoomDto createRoom(RoomDto roomDto);
    public RoomDto deleteRoom(String roomKey);
    public List<RoomDto> findAllRooms();
    public RoomDto findRoomByRoomKey(String roomKey);
}
