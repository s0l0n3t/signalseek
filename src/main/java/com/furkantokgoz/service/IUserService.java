package com.furkantokgoz.service;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.entity.UserEntity;
import com.furkantokgoz.security.jwt.JwtResponse;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;


public interface IUserService {
    public List<UserDto> getAllUsers();
    public UserDto createUser(UserDto userDto);
    public UserDto getUserById(Long id);
    public UserDto getUserByUserKey(String userKey);
    public UserDto updateUser(Long id, UserDto userDto);
    public UserDto deleteUser(String userKey);
    public List<UserDto> getUsersByIpAddress(String ipAddress);
    public List<UserDto> getUserByRoomKey(String roomKey) throws Throwable;
    public UserDto moveUser(String userKey, Double latitude, Double longitude);
    public Boolean isUserExist(String userKey);
    public Boolean isUserAuthorized(String userKey, Authentication authentication);
    public List<UserDto> getGeoLocationByUserKey(String userKey);
}
