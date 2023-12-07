package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookingRoom;

import java.util.List;

public interface IEcomBookingService {

    List<EcomBookingRoom> getAllBookings();
    List<EcomBookingRoom> getAllBookingsByRoomId(Long id);

    void cancelBooking(Long id);

    String saveBooking(Long id, EcomBookingRoom bookingRequest);

    EcomBookingRoom findByBookingConfirmationCode(String confirmationCode);
}
