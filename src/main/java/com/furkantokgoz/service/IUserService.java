package com.furkantokgoz.service;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface IUserService {
    public List<UserDto> getAllUsers();
    public UserDto createUser(UserDto userDto);
    public UserDto getUserById(Long id);
    public UserDto getUserByUserKey(String userKey);
    public UserDto updateUser(Long id, UserDto userDto);
    public UserDto deleteUser(Long id) throws Throwable;
    public UserDto getUserByIpAddress(String ipAddress) throws Throwable;
    public List<UserDto> getUserByRoomKey(String roomKey) throws Throwable;
}
