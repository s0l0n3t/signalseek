package com.furkantokgoz.service;

import com.furkantokgoz.dto.AdminUserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAdminUserService {
    public AdminUserDto createAdminUser(AdminUserDto adminUserDto);
    public AdminUserDto updateAdminUser(AdminUserDto adminUserDto);
    public AdminUserDto deleteAdminUserByUsername(String username);
    public AdminUserDto getAdminuserByUsernameAndPassword(String username, String password);
    public List<AdminUserDto> getAdminUsers();
    public AdminUserDto getAdminUserByUsername(String username);
    public Boolean isAdminUserExist(String username);
    public Boolean isAdminAuthorized(Authentication authentication);
}
