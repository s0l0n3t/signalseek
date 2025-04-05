package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.exception.RoomNotFoundException;
import com.furkantokgoz.mapper.RoomMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RoomService implements IRoomService {
    RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        if(roomRepository.existsByRoomKey(roomDto.getRoomKey())) {
            throw new DuplicateRequestException("Room already exists");
        }
        if(roomDto.getRoomKey().isEmpty()){
            throw new NullPointerException("Room key is empty");
        }
        roomDto.setRoomKey(roomDto.getRoomKey().toLowerCase(Locale.ENGLISH));
        RoomEntity roomEntity = RoomMapper.toEntity(roomDto);
        roomRepository.save(roomEntity);
        return RoomMapper.toDto(roomEntity);
    }
    @Override
    public RoomDto deleteRoom(String roomKey) {
        if(!roomRepository.existsByRoomKey(roomKey)) {
            throw new RoomNotFoundException(roomKey);
        }
        RoomEntity findedRoom = roomRepository.findByRoomKey(roomKey).orElseThrow(() -> new RoomNotFoundException(roomKey));
        roomRepository.deleteById(findedRoom.getId());
        return RoomMapper.toDto(findedRoom);
    }
}
