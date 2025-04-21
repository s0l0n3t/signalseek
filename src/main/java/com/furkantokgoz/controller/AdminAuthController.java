package com.furkantokgoz.controller;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.security.jwt.JwtUtil;
import com.furkantokgoz.service.AdminUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserServiceImpl adminUserService;

    @PostMapping("/login")
    public ResponseEntity createToken(@RequestBody AdminUserDto adminUserDto) throws Exception {
        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            AdminUserDto control = adminUserService.getAdminuserByUsernameAndPassword(adminUserDto.getUsername(), adminUserDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(control));
        //authmanager araştır.
    }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }//error format düzelt
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AdminUserDto> registerUser(@RequestBody AdminUserDto adminUserDto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK
        ).body(adminUserService.createAdminUser(adminUserDto));
    }
}
