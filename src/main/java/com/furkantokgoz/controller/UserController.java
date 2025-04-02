package com.furkantokgoz.controller;

import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.exception.ErrorResponse;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.service.UserServiceImpl;
import com.sun.jdi.request.DuplicateRequestException;
import org.apache.catalina.User;
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
    public List<UserDto> findAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return userDtoList;
    }
    @GetMapping("/room/{roomkey}")
    public ResponseEntity<List<UserDto>> findUsersByRoomkey(@PathVariable (name = "roomkey") String roomkey) {
        List<UserDto> userDtoList = userService.getUserByRoomKey(roomkey);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable (name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }
}
