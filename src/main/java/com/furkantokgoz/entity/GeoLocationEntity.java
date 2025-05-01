package com.furkantokgoz.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "geolocations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Hidden
public class GeoLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double latitude;
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
