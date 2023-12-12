package dev.concat.vab.ecomhotelappbackend.response;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class EcomRoomResponse {
    private Long id;
    private String roomCode;
    private String roomType;
    private BigDecimal roomPrice;

    private boolean idBooked;

    private String photo;

    private List<EcomBookingResponse> bookings;

    public EcomRoomResponse(Long id, String roomCode,String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public EcomRoomResponse(Long id, String roomCode,String roomType, BigDecimal roomPrice, boolean idBooked, byte[] photoBytes, List<EcomBookingResponse> bookings) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.idBooked = idBooked;
        this.photo = photoBytes != null ? Base64.getEncoder().encodeToString(photoBytes) : null ;
        this.bookings = bookings;
    }


    public static EcomRoomResponse fromEcomRoom(EcomRoom ecomRoom) {
        EcomRoomResponse response = new EcomRoomResponse();
        response.setId(ecomRoom.getId());
        response.setRoomCode(ecomRoom.getRoomCode());
        response.setRoomType(ecomRoom.getRoomType());
        response.setRoomPrice(ecomRoom.getRoomPrice());
        // set other fields as needed
        return response;
    }
}
