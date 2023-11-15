package dev.concat.vab.ecomhotelappbackend.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class EcomRoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;

    private boolean idBooked;

    private String photo;

    private List<EcomBookingResponse> bookings;

    public EcomRoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public EcomRoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean idBooked, byte[] photoBytes, List<EcomBookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.idBooked = idBooked;
        this.photo = photoBytes != null ? Base64.getEncoder().encodeToString(photoBytes) : null ;
        this.bookings = bookings;
    }
}
