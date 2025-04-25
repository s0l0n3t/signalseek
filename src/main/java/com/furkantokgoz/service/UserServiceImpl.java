package com.furkantokgoz.service;

import com.furkantokgoz.config.ClientAddressResolver;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.repository.UserRepository;
import com.furkantokgoz.security.jwt.JwtResponse;
import com.sun.jdi.request.DuplicateRequestException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final ClientAddressResolver clientAddressResolver;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository, ClientAddressResolver clientAddressResolver) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.clientAddressResolver = clientAddressResolver;
    }
    @Override
    public UserDto createUser(UserDto userDto){
        if(userRepository.existsByUserKey(userDto.getUserKey())){
//Conflict response error.
            throw new DuplicateRequestException(userDto.getUserKey() + " is already in use");
        }
        if(userDto.getRoomKey() == null || userDto.getRoomKey().isBlank()){ //isblank includes " " but isEmpty is not.
            throw new NullPointerException("roomKey is null");
        }
        userDto.setUserKey(userDto.getUserKey().toLowerCase(Locale.ENGLISH));
        userDto.setRoomKey(userDto.getRoomKey().toLowerCase(Locale.ENGLISH));
        userDto.setIpAddress(clientAddressResolver.Resolve());
        UserEntity userEntity = UserMapper.toEntity(userDto,roomRepository);
        if (userEntity.getIpAddress() == null){
            throw new NullPointerException("ipAddress is null");
        }//business layer security
        if(userEntity.getUserKey() == null){
            throw new NullPointerException("userKey is null");
        }
        if(userEntity.getRoom().getRoomKey() == null){
            throw new NullPointerException("roomKey is null");
        }
            userRepository.save(userEntity);
            return userDto;
    }
    @Override
    public List<UserDto> getAllUsers(){
        List<UserDto> dtos = new ArrayList<>();
        for(int i = 0;i<userRepository.findAll().size();i++){
        dtos.add(UserMapper.toDto(userRepository.findAll().get(i)));}
        return dtos;
    }
    @Override
    public UserDto getUserById(Long id){//null error
        return userRepository.findById(id)
                .map(userEntity -> UserMapper.toDto(userEntity))
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }
    @Override
    public UserDto getUserByUserKey(String userKey){//null error
        return UserMapper.toDto(userRepository.findByUserKey(userKey).orElseThrow(() -> new UserNotFoundException(userKey)));
    }
    @Override
    public UserDto updateUser(Long id, UserDto userDto){
        UserEntity userEntity = UserMapper.toEntity(userDto,roomRepository);
//            UserEntity newUserEntity = userRepository.findById(id)
//                            .orElseGet(() -> userRepository.save(userEntity));
        // if there not exist, it will create. But i don't want to create.
            UserEntity newUserEntity = userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
            newUserEntity.setUserKey(userEntity.getUserKey());
            newUserEntity.setIpAddress(userEntity.getIpAddress());
            newUserEntity.setLatitude(userEntity.getLatitude());
            newUserEntity.setLongitude(userEntity.getLongitude());
            userRepository.save(newUserEntity);
            return userDto;
    }
    @Override
    public UserDto deleteUser(String userKey){
        UserEntity findeduser = userRepository.findByUserKey(userKey).orElseThrow(() -> new UserNotFoundException(userKey));
        userRepository.deleteById(findeduser.getId());
        return UserMapper.toDto(findeduser);
    }
    @Override
    public List<UserDto> getUsersByIpAddress(String ipAddress){
        List<UserDto> userDtoList = new ArrayList<>();
        if(!userRepository.existsByIpAddress(ipAddress)){
            throw new UserNotFoundException(ipAddress);
        }
        List<UserEntity> userEntityList = userRepository.findByIpAddress(ipAddress).orElseThrow(() -> new UserNotFoundException(ipAddress));
        for(UserEntity userEntity : userEntityList){
            userDtoList.add(UserMapper.toDto(userEntity));
        }
        return userDtoList;
    }
    @Override
    public List<UserDto> getUserByRoomKey(String roomKey){
        List<UserDto> userDtoList = new ArrayList<>();
        if(!roomRepository.existsByRoomKey(roomKey)){
            throw new UserNotFoundException(roomKey);
        }
        List<UserEntity> userEntities = userRepository.findByRoom_RoomKey(roomKey).orElseThrow(() -> new UserNotFoundException(roomKey));
        if(userEntities.isEmpty()){
           throw new UserNotFoundException(roomKey + " no user");
        }
        for(UserEntity userEntity : userEntities){
            userDtoList.add(UserMapper.toDto(userEntity));
        }
        return userDtoList;
    }//Internal error
    @Override
    public UserDto moveUser(String userKey, Double latitude, Double longitude){
        UserEntity userEntity = userRepository.findByUserKey(userKey).orElseThrow(() -> new UserNotFoundException(userKey));
        userEntity.setLatitude(latitude);
        userEntity.setLongitude(longitude);
        userRepository.save(userEntity);
        return UserMapper.toDto(userEntity);
    }
    @Override
    public Boolean isUserExist(String userKey){
        return userRepository.existsByUserKey(userKey);
    }
    @Override
    public Boolean isUserAuthorized(String userKey, Authentication authentication){
        if(authentication.getName().equals(userKey) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return true;
        }
        return false;
    }
}
