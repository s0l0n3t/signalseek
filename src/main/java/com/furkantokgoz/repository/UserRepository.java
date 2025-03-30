package com.furkantokgoz.repository;

import com.furkantokgoz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserKey(String userKey);
    Boolean existsByUserKey(String userKey);
    Optional<UserEntity> findByIpAddress(String ipAddress);

    //Optional object is more safe. Throwing exceptions and more.
}
