package com.furkantokgoz.controller;

import com.furkantokgoz.config.LoggerConfigBean;
import com.furkantokgoz.dto.Roles;
import com.furkantokgoz.dto.UserDto;
import com.furkantokgoz.mapper.UserMapper;
import com.furkantokgoz.repository.RoomRepository;
import com.furkantokgoz.security.jwt.JwtFilter;
import com.furkantokgoz.security.jwt.JwtUtil;
import com.furkantokgoz.service.AdminUserServiceImpl;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
import com.furkantokgoz.service.RoomServiceImpl;
import com.furkantokgoz.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="User API")
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
    @Autowired
    private ApplicationLogServiceImpl applicationLogServiceImpl;

    @Operation(summary = "service health check",description = "Everybody can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @GetMapping("/status")
    public ResponseEntity<HttpStatus> controllerTest() {
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }//move to new controller
    @Operation(summary = "creating a user",description = "Everybody can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You created a room"),
            @ApiResponse(responseCode = "404", description = "service out of usable"),
            @ApiResponse(responseCode = "403", description = "if there is not a room or there is a user as this userkey")
    })
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        logger.info(LoggerConfigBean.userCrated(Roles.ROLE_USER,userDto.getUserKey(),applicationLogServiceImpl,userService.getClass().getSimpleName()));
        userDto.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtUtil.generateToken(userDto.getUserKey(),userDto.getAuthorities()));
    }
    @Operation(summary = "find all users",description = "Admin perm can reach")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "you found all users"),
            @ApiResponse(responseCode = "404", description = "service out of usable"),
            @ApiResponse(responseCode = "403", description = "if you don't have admin permission")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info(LoggerConfigBean.userAllCalled(Roles.ROLE_ADMIN,auth.getName(),applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
    @Operation(summary = "updating a user",description = "Every user can reach self endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You updated a room"),
            @ApiResponse(responseCode = "404", description = "service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDto userDto,@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)) {
            logger.info(LoggerConfigBean.userUpdated(userDto.getUserKey(),applicationLogServiceImpl,userService.getClass().getSimpleName(),auth));
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(UserMapper.toEntity(userService.getUserByUserKey(userKey),roomRepository).getId(), userDto));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth,applicationLogServiceImpl,userService.getClass().getName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @Operation(summary = "find a user by id",description = "room perms can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found a room"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity findUserById(@RequestParam Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth)) {
            logger.info(LoggerConfigBean.infoFoundLog(id.toString(),auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(id));
        }
        logger.error(LoggerConfigBean.errorRequestLog(id.toString(),auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @Operation(summary = "find a user by userkey",description = "room perms can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found a room"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping(value = "/find", params = "userKey")
    public ResponseEntity findUserByUserKey(@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.getUserByUserKey(userKey);
        if(roomServiceImpl.isRoomAuthorizedByUserKey(userKey,auth.getName(),auth)){
            logger.info(LoggerConfigBean.infoFoundLog(userKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.FOUND).body(userDto);
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @Operation(summary = "find a user by roomkey",description = "room perms can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found a room"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping(value = "/find", params = "roomKey")
    public ResponseEntity findUsersByRoomkey(@RequestParam String roomKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth) || auth.getName().equals(roomKey)){
            logger.info(LoggerConfigBean.infoFoundLog(roomKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByRoomKey(roomKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(roomKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }//list, users by room
    @Operation(summary = "find a user by ip address",description = "room perms can reach here")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You found a room"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping(value = "/find",params = "ipAddress")
    public ResponseEntity findUserByIpAddress(@RequestParam String ipAddress) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(adminUserServiceImpl.isAdminAuthorized(auth)){
            logger.info(LoggerConfigBean.infoFoundLog(ipAddress,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUsersByIpAddress(ipAddress));
        }
        logger.error(LoggerConfigBean.errorRequestLog(ipAddress,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }//list
    @Operation(summary = "delete a user by userkey",description = "self userkey perms can reach")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You deleted a user"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @DeleteMapping("/deletebyuserkey")
    public ResponseEntity deleteUser(@RequestParam String userKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)){
            logger.info(LoggerConfigBean.userDeleted(Roles.ROLE_USER,auth.getName(),applicationLogServiceImpl,userService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userKey));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
    @Operation(summary = "added a new geolocation",description = "self user perm can reach")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request done"),
            @ApiResponse(responseCode = "404", description = "Service out of usable"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @PostMapping(value = "/move")
    public ResponseEntity moveUser(@RequestParam(name = "userKey") String userKey, @RequestParam(name = "latitude") Double latitude,@RequestParam(name = "longitude") Double longitude) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userService.isUserAuthorized(userKey,auth)){
            logger.info(LoggerConfigBean.infoMapLog(userKey,latitude.toString(),longitude.toString(),userService.getClass().getSimpleName(),applicationLogServiceImpl));
            return ResponseEntity.status(HttpStatus.OK).body(userService.moveUser(userKey,latitude,longitude));
        }
        logger.error(LoggerConfigBean.errorRequestLog(userKey,auth,applicationLogServiceImpl,userService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
    }
}