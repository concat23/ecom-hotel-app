package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.exception.PhotoRetrievalException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.response.EcomBookingResponse;
import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class EcomRoomController {

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


    @DeleteMapping("/backup-restore/{id}")
    public ResponseEntity<String> deleteUpdateBackupAndRestore(@PathVariable Long id) {
        this.iEcomRoomService.deleteUpdateBackupAndRestore(id);
        return ResponseEntity.ok("Entity backup/restore successfully");
    }


    @DeleteMapping("/drop/{id}")
    public ResponseEntity<String> deleteDrop(@PathVariable Long id) {
        this.iEcomRoomService.deleteDrop(id);
        return ResponseEntity.ok("Entity deleted successfully");
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
                            booking.getBookingId(),
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
}
