package com.furkantokgoz.controller;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/status")
    public ResponseEntity controllerTest() {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto());
    }
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }
    @GetMapping(value = "/room", params = "roomKey")
    public ResponseEntity<List<UserDto>> findUsersByRoomkey(@RequestParam String roomKey) {
        List<UserDto> userDtoList = userService.getUserByRoomKey(roomKey);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userDto));
    }
    //find user
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<UserDto> findUserById(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
    @GetMapping(value = "/find", params = "userKey")
    public ResponseEntity<UserDto> findUserByUserKey(@RequestParam String userKey) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserKey(userKey));
    }
    @GetMapping(value = "/find",params = "ipAddress")
    public ResponseEntity<UserDto> findUserByIpAddress(@RequestParam String ipAddress) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByIpAddress(ipAddress));
    }
    @DeleteMapping("/deletebyuserkey")
    public ResponseEntity<UserDto> deleteUser(@RequestParam String userKey) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userKey));
    }


}
