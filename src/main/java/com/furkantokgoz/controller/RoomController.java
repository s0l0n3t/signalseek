package com.furkantokgoz.controller;

import com.furkantokgoz.dto.RoomDto;
import com.furkantokgoz.service.RoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RoomServiceImpl roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);
        logger.info(roomDto.getRoomKey()+" room created");
        return ResponseEntity.status(HttpStatus.CREATED).body(roomDto);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<RoomDto> deleteRoom(@RequestParam String roomKey) {
        logger.info(roomKey+" room deleted");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(roomKey));
    }
    //add find endpoints
    @GetMapping(value = "/all")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        logger.info("getAllRooms called");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAllRooms());
    }
    @GetMapping(value = "/find", params = "roomKey")
    public ResponseEntity<RoomDto> findRoomByRoomKey(@RequestParam String roomKey) {
        logger.info(roomKey+" room found");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findRoomByRoomKey(roomKey));
    }//find room information
}
