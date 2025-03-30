package com.furkantokgoz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "userkey")
    private String userKey;
    @Column(name = "ipaddress")
    private String ipAddress; //Max 15 char
    @Column(name = "latitude")
    private Double latitude; // 10,8
    @Column(name = "longitude")
    private Double longitude;
}
