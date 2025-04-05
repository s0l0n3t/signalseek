package com.furkantokgoz.repository;

import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    Optional<RoomEntity> findByRoomKey(String roomKey);
    Optional<RoomEntity> findById(Long id);
    Optional<List<UserEntity>> findUsersByRoomKey(String roomKey);
    boolean existsByRoomKey(String roomKey);
}
