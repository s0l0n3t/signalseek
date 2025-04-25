package com.furkantokgoz.service;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.exception.RoomNotFoundException;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.mapper.RoomMapper;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Override
    public Boolean isRoomExist(String roomKey) {
        return roomRepository.existsByRoomKey(roomKey);
    }
    @Override
    public Boolean isRoomAuthorized(String roomKey, Authentication authentication) {
            if(authentication.getName().equals(roomKey)) {
                return true;
            }
        return false;//add admin
    }
    @Override
    public Boolean isRoomAuthorizedByUserKey(String userKey,String roomKey, Authentication authentication) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return true;
        }
        return userRepository.findByRoom_RoomKey(roomKey)
                .orElseThrow(() -> new RoomNotFoundException(roomKey))
                .stream()
                .anyMatch(user -> userKey.equals(user.getUserKey()));
    }//no need to convert to Dto.
}
