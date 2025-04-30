package com.furkantokgoz.service;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.entity.AdminUserEntity;
import com.furkantokgoz.exception.UserNotFoundException;
import com.furkantokgoz.mapper.AdminUserMapper;
import com.furkantokgoz.repository.AdminUserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AdminUserServiceImpl implements IAdminUserService{


    private final PasswordEncoder passwordEncoder;
    private AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserServiceImpl(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminUserDto createAdminUser(AdminUserDto adminUserDto) {
        if(adminUserRepository.existsByUsername(adminUserDto.getUsername())) {
            throw new DuplicateRequestException(adminUserDto.getUsername() + " already exists");
        }
        if(adminUserRepository.existsByEmail(adminUserDto.getEmail())){
            throw new DuplicateRequestException(adminUserDto.getEmail() + " already exists");
        }
        adminUserDto.setEmail(adminUserDto.getEmail().toLowerCase(Locale.ENGLISH));
        adminUserDto.setUsername(adminUserDto.getUsername().toLowerCase(Locale.ENGLISH));
        adminUserDto.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        AdminUserEntity adminUserEntity = AdminUserMapper.toEntity(adminUserDto);
        if(adminUserDto.getEmail() == null){
            throw new NullPointerException("Email is null");
        }
        if(adminUserDto.getUsername() == null){
            throw new NullPointerException("Username is null");
        }
        if(adminUserDto.getPassword() == null){
            throw new NullPointerException("Password is null");
        }
        adminUserRepository.save(adminUserEntity);
        return adminUserDto;
    }

    @Override
    public AdminUserDto updateAdminUser(AdminUserDto adminUserDto) {
        AdminUserEntity adminUserEntity = AdminUserMapper.toEntity(adminUserDto);
        for(AdminUserEntity adminUser : adminUserRepository.findAllByOrderByIdDesc()){
            if(!adminUserDto.getUsername().equals(adminUser.getUsername())){
                throw new UserNotFoundException(adminUserDto.getUsername() + " not found");
            }
        }
        return AdminUserMapper.toDto(AdminUserEntity.builder().id(adminUserEntity.getId())
                .email(adminUserEntity.getEmail())
                .username(adminUserEntity.getUsername())
                .password(adminUserEntity.getPassword())
                .build());
    }

    @Override
    public AdminUserDto deleteAdminUserByUsername(String username) {
        AdminUserDto adminUserDto = AdminUserMapper.toDto(adminUserRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username)));
        adminUserRepository.deleteById(adminUserDto.getId());//dto and entity had long variables so it's true.
        return adminUserDto;
    }

    @Override
    public AdminUserDto getAdminuserByUsernameAndPassword(String username, String password) {
        AdminUserEntity adminUserEntity = adminUserRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
        if(!passwordEncoder.matches(password,adminUserEntity.getPassword())){

            throw new UserNotFoundException(username + " not found");
        }
        return AdminUserMapper.toDto(adminUserEntity);
    }

    @Override
    public List<AdminUserDto> getAdminUsers() {
        List<AdminUserDto> adminUserDtoList = new ArrayList<>();
        for(AdminUserEntity adminUserEntity : adminUserRepository.findAll()){
            adminUserDtoList.add(AdminUserMapper.toDto(adminUserEntity));
        }
        return adminUserDtoList;
    }
    @Override
    public AdminUserDto getAdminUserByUsername(String username) {
        return AdminUserMapper.toDto(adminUserRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username)));
    }
    @Override
    public Boolean isAdminUserExist(String username) {
        return adminUserRepository.existsByUsername(username);
    }
    @Override
    public Boolean isAdminAuthorized(Authentication authentication) {
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            return true;
        }
        return false;
    }

}
