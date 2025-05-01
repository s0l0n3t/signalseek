package com.furkantokgoz.repository;

import com.furkantokgoz.dto.AdminUserDto;
import com.furkantokgoz.entity.AdminUserEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Hidden
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUserEntity,Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<AdminUserEntity> findByUsernameAndPassword(String username, String password);
    Optional<AdminUserEntity> findByEmail(String email);
    Optional<AdminUserEntity> findByUsername(String username);
    List<AdminUserEntity> findAllByOrderByIdDesc();
}
