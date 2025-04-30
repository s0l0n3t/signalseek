package com.furkantokgoz.repository;

import com.furkantokgoz.entity.GeoLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GeoLocationRepository extends JpaRepository<GeoLocationEntity,Long> {
    List<GeoLocationEntity> findByUserId(long userId);
}
