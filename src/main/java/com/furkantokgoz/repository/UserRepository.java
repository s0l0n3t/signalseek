package com.furkantokgoz.repository;

import com.furkantokgoz.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserKey(String userKey);
    Boolean existsByUserKey(String userKey);
    Optional<List<UserEntity>> findByIpAddress(String ipAddress);
    Optional<List<UserEntity>> findByRoom_RoomKey(String roomRoomKey);
    Boolean existsByIpAddress(String ipAddress);
    Boolean existsByRoom_RoomKey(String roomRoomKey);
    //Optional object is more safe. Throwing exceptions and more.
}
