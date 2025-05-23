package com.furkantokgoz.controller;

import com.furkantokgoz.config.LoggerConfigBean;
import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.dto.Roles;
import com.furkantokgoz.security.jwt.JwtUtil;
import com.furkantokgoz.service.AdminUserServiceImpl;
import com.furkantokgoz.service.ApplicationLogServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@Tag(name="Admin API")
@RequestMapping("/admin")
public class AdminAuthController {
    private final static Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminUserServiceImpl adminUserService;
    @Autowired
    private ApplicationLogServiceImpl applicationLogService;
    @Operation(summary = "admin login endpoint",description = "Everybody can reach this endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You reach admin Json Web Token"),
            @ApiResponse(responseCode = "404", description = "service out of usable"),
            @ApiResponse(responseCode = "403", description = "username or password uncorrect.")
    })
    @PostMapping("/login")
    public ResponseEntity adminLogin(@RequestBody AdminUserDto adminUserDto) {
        try{
            AdminUserDto control = adminUserService.getAdminuserByUsernameAndPassword(adminUserDto.getUsername(), adminUserDto.getPassword());
            control.setGetAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            logger.info(LoggerConfigBean.loginAuthLog(control.getUsername(),applicationLogService,adminUserService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(control.getUsername(),control.getGetAuthorities()));
    }catch (BadCredentialsException e){
            logger.error(LoggerConfigBean.errorAuthLog(adminUserDto.getUsername(),applicationLogService,adminUserService.getClass().getSimpleName()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggerConfigBean.UNAUTHENTICATED);
        }//error format düzelt
        catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "admin login endpoint",description = "Everybody can reach this endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You created an account"),
            @ApiResponse(responseCode = "404", description = "service out of usable")
    })
    @PostMapping("/register")
    public ResponseEntity<AdminUserDto> registerUser(@RequestBody AdminUserDto adminUserDto) {
        logger.info(LoggerConfigBean.userCrated(Roles.ROLE_ADMIN,adminUserDto.getUsername(),applicationLogService,adminUserService.getClass().getSimpleName()));
        return ResponseEntity.status(HttpStatus.OK
        ).body(adminUserService.createAdminUser(adminUserDto));
    }
}
