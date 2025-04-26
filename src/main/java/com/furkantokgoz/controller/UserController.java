package com.furkantokgoz.controller;

import com.furkantokgoz.config.LoggerConfigBean;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.security.jwt.JwtFilter;
import com.furkantokgoz.security.jwt.JwtUtil;
import com.furkantokgoz.service.AdminUserServiceImpl;
import com.furkantokgoz.service.RoomServiceImpl;
import com.furkantokgoz.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private RoomServiceImpl roomServiceImpl;
    @Autowired
    private AdminUserServiceImpl adminUserServiceImpl;

    @GetMapping("/status")
    public ResponseEntity<HttpStatus> controllerTest() {
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }//move to new controller
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        logger.info("User created: " + userDto);
        userDto.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtUtil.generateToken(userDto.getUserKey(),userDto.getAuthorities()));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        logger.info("all user called");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDto userDto,@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)) {
            logger.info("User updated: " + userDto);
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(UserMapper.toEntity(userService.getUserByUserKey(userKey),roomRepository).getId(), userDto));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    //find user
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity findUserById(@RequestParam Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth)) {
            logger.info(LoggerConfigBean.infoFoundLog(id.toString(),auth));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(id));
        }
        logger.error(LoggerConfigBean.errorRequestLog(id.toString(),auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @GetMapping(value = "/find", params = "userKey")
    public ResponseEntity findUserByUserKey(@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.getUserByUserKey(userKey);
        if(roomServiceImpl.isRoomAuthorizedByUserKey(userKey,auth.getName(),auth)){
            logger.info(LoggerConfigBean.infoFoundLog(userKey,auth));
            return ResponseEntity.status(HttpStatus.FOUND).body(userDto);
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @GetMapping(value = "/find", params = "roomKey")
    public ResponseEntity findUsersByRoomkey(@RequestParam String roomKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth) || auth.getName().equals(roomKey)){
            logger.info(LoggerConfigBean.infoFoundLog(roomKey,auth));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByRoomKey(roomKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(roomKey,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }//list, users by room
    @GetMapping(value = "/find",params = "ipAddress")
    public ResponseEntity findUserByIpAddress(@RequestParam String ipAddress) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth)){
            logger.info(LoggerConfigBean.infoFoundLog(ipAddress,auth));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUsersByIpAddress(ipAddress));
        }
        logger.error(LoggerConfigBean.errorRequestLog(ipAddress,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }//list
    @DeleteMapping("/deletebyuserkey")
    public ResponseEntity deleteUser(@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)){
            logger.info("User deleted: " + userKey);
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @PostMapping(value = "/move")
    public ResponseEntity moveUser(@RequestParam(name = "userKey") String userKey, @RequestParam(name = "latitude") Double latitude,@RequestParam(name = "longitude") Double longitude) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)){
            logger.info(LoggerConfigBean.infoMapLog(userKey,latitude.toString(),longitude.toString()));
            return ResponseEntity.status(HttpStatus.OK).body(userService.moveUser(userKey,latitude,longitude));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
}