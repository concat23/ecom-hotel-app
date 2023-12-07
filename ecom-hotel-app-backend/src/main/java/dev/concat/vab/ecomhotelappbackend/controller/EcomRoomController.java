package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.exception.PhotoRetrievalException;
import dev.concat.vab.ecomhotelappbackend.exception.ResourceNotFoundException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.response.EcomBookingResponse;
import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import dev.concat.vab.ecomhotelappbackend.response.HttpResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoomService;
import dev.concat.vab.ecomhotelappbackend.utils.CustomOptional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class EcomRoomController {
    private static final Logger log = LoggerFactory.getLogger(EcomRoomController.class);
    private final IEcomRoomService iEcomRoomService;
    private final IEcomBookingService iEcomBookingService;

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


    @GetMapping(path="/all-rooms")
    public ResponseEntity<List<EcomRoomResponse>> getAllRooms(){
        List<EcomRoom> rooms = this.iEcomRoomService.roomList();
        List<EcomRoomResponse> roomsRes = new ArrayList<>();

        for(EcomRoom room : rooms){
            byte[] photoBytes = this.iEcomRoomService.getRoomPhotoByRoomId(room.getId());

            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                EcomRoomResponse ecomRoomResponse = getEcomRoomResponse(room);
                ecomRoomResponse.setPhoto(base64Photo);
                roomsRes.add(ecomRoomResponse);
            }
        }
        return ResponseEntity.ok(roomsRes);
    }


    @PutMapping(path="/update/{id}")
    public ResponseEntity<EcomRoomResponse> updateRoom(@PathVariable("id") Long roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {

        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : this.iEcomRoomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;

        EcomRoom theRoom = this.iEcomRoomService.updateRoom(roomId, roomType, roomPrice, photoBytes);
        theRoom.setPhoto(photoBlob);

//        Map<String, Object> map = new HashMap<>();
//        map.put("id", String.valueOf(roomId));
//        map.put("roomType", roomType);
//        map.put("roomPrice", roomPrice);
//        map.put("photo", (photoBlob != null) ? Base64.getEncoder().encodeToString(photoBytes) : null);
//
//        HttpResponse response = HttpResponse.builder()
//                .message("Updated room")
//                .developerMessage("Handing the room update feature")
//                .path("/update/"+roomId)
//                .status(HttpStatus.OK)
//                .statusCode(HttpStatus.OK.value())
//                .requestMethod("PUT")
//                .timeStamp(now().toString())
//                .data(map)
//                .build();
//        log.info("updateRoom:method > response:variable : "+response );

//        return ResponseEntity.created(getUri()).body(response);

    EcomRoomResponse roomResponse = getEcomRoomResponse(theRoom);

    return ResponseEntity.ok(roomResponse);

    }

    @GetMapping(path = "/room/{id}")
    public ResponseEntity<EcomRoomResponse> getEcomRoomById(@PathVariable("id") Long id) {
        EcomRoom theRoom = this.iEcomRoomService.getEcomRoomId(id);

        if (theRoom == null) {
            throw new ResourceNotFoundException("Room not found for id: " + id);
        }

        log.info("getEcomRoomById: " + theRoom);

        EcomRoomResponse ecomRoomResponse = getEcomRoomResponse(theRoom);
        return ResponseEntity.ok(ecomRoomResponse);
    }




    @DeleteMapping("/backup-restore/room/{id}")
    public ResponseEntity<Void> deleteUpdateBackupAndRestore(@PathVariable("id") Long id) {
        this.iEcomRoomService.deleteUpdateBackupAndRestoreRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/drop/{id}")
    public ResponseEntity<String> deleteDrop(@PathVariable("id") Long id) {
        this.iEcomRoomService.deleteDropRoom(id);
        return ResponseEntity.ok("Entity deleted successfully");
    }

    @DeleteMapping(path="/delete/room/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Long id){
        this.iEcomRoomService.deleteDropRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<EcomRoomResponse> convertToResponseList(List<EcomRoom> rooms) {
        return rooms.stream()
                .map(EcomRoomResponse::fromEcomRoom)
                .collect(Collectors.toList());
    }

    private EcomRoomResponse getEcomRoomResponse(EcomRoom room){
        List<EcomBookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        List<EcomBookingResponse> bookingInfoRes = new ArrayList<>();

        if (bookings != null) {
            bookingInfoRes = bookings.stream()
                    .map(booking -> new EcomBookingResponse(
                            booking.getId(),
                            booking.getCheckInDate(),
                            booking.getCheckOutDate(),
                            booking.getBookingConfirmationCode()))
                    .collect(Collectors.toList());
        } else {
            bookingInfoRes = new ArrayList<>();
        }
            byte[] photoBytes = null;

            Blob photoBlob = room.getPhoto();

            if( photoBlob != null){
                try{
                    photoBytes = photoBlob.getBytes(1,(int) photoBlob.length());
                }catch(SQLException exc){
                    throw new PhotoRetrievalException("Error, Retrieval photo !");
                 }
            }

            return new EcomRoomResponse(room.getId(),room.getRoomType(),room.getRoomPrice(),room.isBooked(), photoBytes,bookingInfoRes);

    }

    private List<EcomBookedRoom> getAllBookingsByRoomId(Long id){
        return this.iEcomBookingService.getAllBookingsByRoomId(id);
    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/rooms/room/<id>").toUriString());
    }
}
