package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.exception.InvalidEcomBookingRequestException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookedRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomBookedRoomRepository;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoomRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EcomBookingServiceImpl implements IEcomBookingService {

    private final IEcomBookedRoomRepository iEcomBookedRoomRepository;
    private final IEcomRoomRepository iEcomRoomRepository;

    @Override
    public List<EcomBookedRoom> getAllBookings() {
        log.info("Loading: getAllBookings");
        return  iEcomBookedRoomRepository.findAll();
    }

    @Override
    public void cancelBooking(Long id) {
        log.info("Canceling ...");
        iEcomBookedRoomRepository.deleteById(id);
        log.info("Cancel successfully. ID: {}",id);
    }

    @Override
    public String saveBooking(Long id, EcomBookedRoom bookingRequest) {
        log.info("Saving ...");
//        if (bookingRequest.getCheckInDate() == null || bookingRequest.getCheckOutDate() == null) {
//            throw new InvalidEcomBookingRequestException("Check-in and check-out dates must be specified");
//        }

        if (bookingRequest.getCheckInDate().isAfter(bookingRequest.getCheckOutDate())) {
            throw new InvalidEcomBookingRequestException("Check-in date must come before check-out date");
        }


        EcomRoom room = iEcomRoomRepository.getRoomById(id);
        List<EcomBookedRoom> existingBookings = room.getBookings();

        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if(roomIsAvailable){
            room.addBooking(bookingRequest);
            iEcomBookedRoomRepository.save(bookingRequest);
            log.info("Booking saved successfully. Confirmation code: {}", bookingRequest.getBookingConfirmationCode());
        }else{
            log.info("Room not available for selected dates. Room ID: {}", room.getId());
            throw new InvalidEcomBookingRequestException("Sorry, this room is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public EcomBookedRoom findByBookingConfirmationCode(String confirmationCode) {
        log.info("Finding: findByBookingConfirmationCode ...");
        return this.iEcomBookedRoomRepository.findByBookingConfirmationCode(confirmationCode);
    }


    @Override
    public List<EcomBookedRoom> getAllBookingsByRoomId(Long id) {
        log.info("Getting: getAllBookingsByRoomId ...");
        return iEcomBookedRoomRepository.findByRoomId(id);
    }

    private boolean roomIsAvailable(EcomBookedRoom bookingRequest, List<EcomBookedRoom> existingBookings){

        return existingBookings
                .stream()
                .noneMatch(existingBooking
                        -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())

                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())

                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                            && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                            && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                            && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                            && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                            && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );


    }



}
