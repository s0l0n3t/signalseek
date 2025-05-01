package com.furkantokgoz.entity;


import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "navigator_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Hidden
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, updatable = false)
    private long id;
    @Column(name = "userkey",length = 50,nullable = false)
    private String userKey;
    @Column(name = "ipaddress",length = 15,nullable = false)
    private String ipAddress;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<GeoLocationEntity> geolocations = new ArrayList<GeoLocationEntity>();
}
