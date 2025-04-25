package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IRoomService {
    public RoomDto createRoom(RoomDto roomDto);
    public RoomDto deleteRoom(String roomKey);
    public List<RoomDto> findAllRooms();
    public RoomDto findRoomByRoomKey(String roomKey);
    public Boolean isRoomExist(String roomKey);
    public Boolean isRoomAuthorized(String roomKey, Authentication authentication);
    public Boolean isRoomAuthorizedByUserKey(String userKey,String roomKey, Authentication authentication);
}
