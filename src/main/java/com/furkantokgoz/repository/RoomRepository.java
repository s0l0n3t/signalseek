package com.furkantokgoz.repository;

import com.furkantokgoz.entity.RoomEntity;
import com.furkantokgoz.entity.UserEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Hidden
@Repository //new added.
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    Optional<RoomEntity> findByRoomKey(String roomKey);
    Optional<RoomEntity> findById(Long id);
    boolean existsByRoomKey(String roomKey);
}
