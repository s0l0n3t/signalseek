package com.furkantokgoz.service;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDto createUser(UserDto userDto){
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
    public ResponseEntity<UserDto> getUserById(Long id){//null error
        return userRepository.findById(id)
                .map(userEntity -> ResponseEntity.ok(UserMapper.toDto(userEntity)))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    @Override
    public UserDto getUserByUserKey(String userKey){//null error
        return UserMapper.toDto(userRepository.findByUserKey(userKey).orElseThrow(() -> new ResourceNotFoundException("User not found")));
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
    public UserDto deleteUser(Long id) throws Throwable{
        UserEntity findeduser = userRepository.findById(id).orElseThrow(() -> new Throwable("User not found"));
        userRepository.deleteById(id);
        return UserMapper.toDto(findeduser);
    }

    //getipaddressmetod
}
