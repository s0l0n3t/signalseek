package com.furkantokgoz.service;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDto createUser(UserDto userDto){
        userDto.setUserKey(userDto.getUserKey().toLowerCase(Locale.ENGLISH));
        userDto.setRoomKey(userDto.getRoomKey().toLowerCase(Locale.ENGLISH));
        if(userRepository.existsByUserKey(userDto.getUserKey())){
//Conflict response error.
            throw new DuplicateRequestException(userDto.getUserKey() + " already exists");
        }
            UserEntity userEntity = UserMapper.toEntity(userDto);
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
        UserEntity userEntity = UserMapper.toEntity(userDto);
            UserEntity newUserEntity = userRepository.findById(id)
                            .orElseGet(() -> userRepository.save(userEntity));
        // if there not exist, it will create
            newUserEntity.setUserKey(userEntity.getUserKey());
            newUserEntity.setIpAddress(userEntity.getIpAddress());
            newUserEntity.setLatitude(userEntity.getLatitude());
            newUserEntity.setLongitude(userEntity.getLongitude());
            userRepository.save(newUserEntity);
            return userDto;
    }
    @Override
    public UserDto deleteUser(Long id) throws Throwable{
        UserEntity findeduser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
        userRepository.deleteById(id);
        return UserMapper.toDto(findeduser);
    }
    @Override
    public UserDto getUserByIpAddress(String ipAddress) throws Throwable{
        return  UserMapper.toDto(userRepository.findByIpAddress(ipAddress).orElseThrow(() -> new UserNotFoundException(ipAddress)));
    }
    @Override
    public List<UserDto> getUserByRoomKey(String roomKey){
        List<UserDto> userDtoList = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByRoomKey(roomKey).orElseThrow(() -> new ResourceNotFoundException("Roomkey not found"));
        for(UserEntity userEntity : userEntities){
            userDtoList.add(UserMapper.toDto(userEntity));
        }
        return userDtoList;
    }
}
