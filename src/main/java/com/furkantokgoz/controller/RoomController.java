package com.furkantokgoz.controller;

import com.furkantokgoz.config.LoggerConfigBean;
import com.furkantokgoz.dto.Roles;
import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.security.jwt.JwtUtil;
import com.furkantokgoz.service.AdminUserServiceImpl;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
import com.furkantokgoz.service.RoomServiceImpl;
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
@RequestMapping("/room")
public class RoomController {

    private final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RoomServiceImpl roomService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminUserServiceImpl adminUserServiceImpl;
    @Autowired
    private ApplicationLogServiceImpl applicationLogServiceImpl;

    @PostMapping("/create")
    public ResponseEntity createRoom(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
        logger.info(LoggerConfigBean.userCrated(Roles.ROLE_ROOM,roomDto.getRoomKey(),applicationLogServiceImpl,roomService.getClass().getSimpleName()));
        roomDto.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ROOM")));
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtUtil.generateToken(roomDto.getRoomKey(),roomDto.getAuthorities()));
    }//token output
    @DeleteMapping("/delete")
    public ResponseEntity deleteRoom(@RequestParam String roomKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(roomService.isRoomAuthorized(roomKey,auth)){
            logger.info(LoggerConfigBean.userDeleted(Roles.ROLE_ROOM,roomKey,applicationLogServiceImpl,roomService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(roomKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(roomKey,auth,applicationLogServiceImpl,roomService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        logger.info(LoggerConfigBean.userAllCalled(Roles.ROLE_ROOM,SecurityContextHolder.getContext().getAuthentication().getName(), applicationLogServiceImpl, roomService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAllRooms());
    }
    @GetMapping(value = "/find", params = "roomKey")
    public ResponseEntity findRoomByRoomKey(@RequestParam String roomKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(roomService.isRoomAuthorized(roomKey,auth) || adminUserServiceImpl.isAdminAuthorized(auth)){
            logger.info(LoggerConfigBean.infoFoundLog(roomKey,auth,applicationLogServiceImpl,roomService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.OK).body(roomService.findRoomByRoomKey(roomKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(roomKey,auth,applicationLogServiceImpl,roomService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
}
