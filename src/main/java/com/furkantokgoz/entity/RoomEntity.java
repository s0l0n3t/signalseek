package com.furkantokgoz.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Hidden
public class RoomEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique=true,name = "roomkey",nullable = false)
    private String roomKey;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<UserEntity> userEntityList= new ArrayList<UserEntity>();
}
