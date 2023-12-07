package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;

import java.util.List;

public interface IEcomBookingService {

    List<EcomBookedRoom> getAllBookings();
    List<EcomBookedRoom> getAllBookingsByRoomId(Long id);

    void cancelBooking(Long id);

    String saveBooking(Long id, EcomBookedRoom bookingRequest);

    EcomBookedRoom findByBookingConfirmationCode(String confirmationCode);
}
