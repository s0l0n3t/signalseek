package com.furkantokgoz.mapper;

import com.furkantokgoz.dto.GeoLocationDto;
import com.furkantokgoz.entity.GeoLocationEntity;

public class GeoLocationMapper {
    public static GeoLocationDto toDto(GeoLocationEntity geoLocationEntity) {
        return GeoLocationDto.builder()
                .id(geoLocationEntity.getId())
                .longitude(geoLocationEntity.getLongitude())
                .latitude(geoLocationEntity.getLatitude())
                .build();
    }
    public static GeoLocationEntity toEntity(GeoLocationDto geoLocationDto) {
        return GeoLocationEntity.builder()
                .id(geoLocationDto.getId())
                .longitude(geoLocationDto.getLongitude())
                .latitude(geoLocationDto.getLatitude())
                .build();
    }
}
