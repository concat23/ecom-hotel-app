package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;

import java.util.List;

public interface IEcomBookingService {
    List<EcomBookedRoom> getAllBookingsByRoomId(Long id);
}
