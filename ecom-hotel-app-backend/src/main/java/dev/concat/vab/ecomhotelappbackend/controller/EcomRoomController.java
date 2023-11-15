package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class EcomRoomController {

    private final IEcomRoomService iEcomRoomService;


//    @GetMapping(path="/room/types")
//    public ResponseEntity<List<EcomRoomResponse>> roomList(){
//        List<EcomRoom> list = this.iEcomRoomService.roomList();
//        List<EcomRoomResponse> listRes = this.convertToResponseList(list);
//        return ResponseEntity.ok(listRes);
//    }

    @GetMapping(path="/room/types")
    public List<String> roomList(){
        return this.iEcomRoomService.getAllRoomTypes();
    }
    @PostMapping(path = "/add/new-room")
    public ResponseEntity<EcomRoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice){

        EcomRoom savedRoom = this.iEcomRoomService.addNewRoom(photo,roomType,roomPrice);
        EcomRoomResponse roomResponse = new EcomRoomResponse(savedRoom.getId(), savedRoom.getRoomType(),savedRoom.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }


    private List<EcomRoomResponse> convertToResponseList(List<EcomRoom> rooms) {
        return rooms.stream()
                .map(EcomRoomResponse::fromEcomRoom)
                .collect(Collectors.toList());
    }

}
