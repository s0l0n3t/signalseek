package com.furkantokgoz.controller;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/status")
    public ResponseEntity<HttpStatus> controllerTest() {
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        logger.info("User created: " + userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@RequestParam Long id) {
        logger.info("User updated: " + userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userDto));
    }
    //find user
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<UserDto> findUserById(@RequestParam Long id) {
        logger.info("User found: " + id);
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(id));
    }//unique
    @GetMapping(value = "/find", params = "userKey")
    public ResponseEntity<UserDto> findUserByUserKey(@RequestParam String userKey) {
        logger.info("User found: " + userKey);
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByUserKey(userKey));
    }//unique
    @GetMapping(value = "/find", params = "roomKey")
    public ResponseEntity<List<UserDto>> findUsersByRoomkey(@RequestParam String roomKey) {
        logger.info("User found: " + roomKey);
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByRoomKey(roomKey));
    }//list, working but internal error
    @GetMapping(value = "/find",params = "ipAddress")
    public ResponseEntity<List<UserDto>> findUserByIpAddress(@RequestParam String ipAddress) {
        logger.info("User found: " + ipAddress);
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUsersByIpAddress(ipAddress));
    }//list
    @DeleteMapping("/deletebyuserkey")
    public ResponseEntity<UserDto> deleteUser(@RequestParam String userKey) {
        logger.info("User deleted: " + userKey);
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userKey));
    }


}
