package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.exception.RoomNotFoundException;
import com.furkantokgoz.mapper.RoomMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RoomServiceImpl implements IRoomService {
    private final UserRepository userRepository;
    RoomRepository roomRepository;
    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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
        return RoomMapper.toDto(roomEntity,userRepository);
    }
    @Override
    public RoomDto deleteRoom(String roomKey) {
        if(!roomRepository.existsByRoomKey(roomKey)) {
            throw new RoomNotFoundException(roomKey);
        }
        RoomEntity findedRoom = roomRepository.findByRoomKey(roomKey).orElseThrow(() -> new RoomNotFoundException(roomKey));
        roomRepository.deleteById(findedRoom.getId());
        return RoomMapper.toDto(findedRoom,userRepository);
    }
    @Override
    public List<RoomDto> findAllRooms() {
        if(roomRepository.findAll().isEmpty()) {
            throw new RoomNotFoundException("No room data");
        }
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        List<RoomDto> roomDtoList = new ArrayList<>();
        for(RoomEntity roomEntity : roomEntityList) {
            roomDtoList.add(RoomMapper.toDto(roomEntity,userRepository));
        }
        return roomDtoList;
    }
    @Override
    public RoomDto findRoomByRoomKey(String roomKey) {
        return RoomMapper.toDto(roomRepository.findByRoomKey(roomKey).orElseThrow(() -> new RoomNotFoundException(roomKey)),userRepository);
    }
}
