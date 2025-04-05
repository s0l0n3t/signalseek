package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;

public interface IRoomService {
    public RoomDto createRoom(RoomDto roomDto);
    public RoomDto deleteRoom(String roomKey);
}
