package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcomBookingServiceImpl implements IEcomBookingService {
    @Override
    public List<EcomBookedRoom> getAllBookingsByRoomId(Long id) {
        return null;
    }
}
