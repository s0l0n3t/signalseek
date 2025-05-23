package com.furkantokgoz.repository;

import com.furkantokgoz.entity.GeoLocationEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Hidden
@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocationEntity,Long> {
    List<GeoLocationEntity> findByUserId(long userId);
}
