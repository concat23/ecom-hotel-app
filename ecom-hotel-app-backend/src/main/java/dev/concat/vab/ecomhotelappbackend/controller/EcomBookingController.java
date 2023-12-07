package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.exception.InvalidEcomBookingRequestException;
import dev.concat.vab.ecomhotelappbackend.exception.ResourceNotFoundException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.response.EcomBookingResponse;
import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class EcomBookingController {
    private final IEcomBookingService iEcomBookingService;
    private final IEcomRoomService iEcomRoomService;

    @GetMapping(path = "/all-bookings")
    public ResponseEntity<List<EcomBookingResponse>> getAllBookings(){
        List<EcomBookedRoom> bookings = iEcomBookingService.getAllBookings();
        List<EcomBookingResponse> bookingResponses = new ArrayList<>();
        for(EcomBookedRoom booking : bookings ){
            EcomBookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping(path = "/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            EcomBookedRoom booking = iEcomBookingService.findByBookingConfirmationCode(confirmationCode);
            EcomBookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException exc){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
        }
    }

    @PostMapping(path="/room/{id}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long id,@RequestBody EcomBookedRoom bookingRequest){
        try{
            String confirmationCode = iEcomBookingService.saveBooking(id,bookingRequest);
            return ResponseEntity.ok("Room booked successfully, Your booking confirmation code is: "+confirmationCode);
        }catch(InvalidEcomBookingRequestException exc){
            return ResponseEntity.badRequest().body(exc.getMessage());
        }
    }

    @DeleteMapping(path="/booking/{id}/delete")
    public void cancelBooking(@PathVariable Long id){
        iEcomBookingService.cancelBooking(id);
    }

    public EcomBookingResponse getBookingResponse(EcomBookedRoom booking){
        EcomRoom theRoom = iEcomRoomService.getEcomRoomId(booking.getId());
        EcomRoomResponse room = new EcomRoomResponse(
                                    theRoom.getId(),
                                    theRoom.getRoomType(),
                                    theRoom.getRoomPrice()
                                );
        return new EcomBookingResponse(
                                        booking.getId(),
                                        booking.getCheckInDate(),
                                        booking.getCheckOutDate(),
                                        booking.getGuestFullName(),
                                        booking.getGuestEmail(),
                                        booking.getNumOfAdults(),
                                        booking.getNumOfChildren(),
                                        booking.getTotalNumOfGuest(),
                                        booking.getBookingConfirmationCode(),
                                        room);


    }
}
