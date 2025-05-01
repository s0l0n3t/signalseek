package com.furkantokgoz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocationDto {
    @Schema(name = "geolocation id",example = "1", required = true)
    private long id;
    @Schema(name = "user latitude")
    private double latitude;
    @Schema(name = "user longitude")
    private double longitude;
}
