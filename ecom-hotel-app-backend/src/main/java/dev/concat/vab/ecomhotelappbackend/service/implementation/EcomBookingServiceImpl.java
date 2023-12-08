package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.exception.InvalidEcomBookingRequestException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookingRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomBookingRepository;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoomRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EcomBookingServiceImpl implements IEcomBookingService {

    private final IEcomBookingRepository iEcomBookingRepository;
    private final IEcomRoomRepository iEcomRoomRepository;

    @Override
    public List<EcomBookingRoom> getAllBookings() {
        log.info("Loading: getAllBookings");
        return iEcomBookingRepository.findAll();
    }

    @Override
    public void cancelBooking(Long id) {
        log.info("Canceling ...");
        iEcomBookingRepository.deleteById(id);
        log.info("Cancel successfully. ID: {}", id);
    }

    @Override
    public String saveBooking(Long id, EcomBookingRoom bookingRequest) {
        log.info("Saving ...");
        if (bookingRequest.getCheckInDate() == null || bookingRequest.getCheckOutDate() == null) {
            throw new InvalidEcomBookingRequestException("Check-in and check-out dates must be specified");
        }

        if (bookingRequest.getCheckInDate().isAfter(bookingRequest.getCheckOutDate())) {
            throw new InvalidEcomBookingRequestException("Check-in date must come before check-out date");
        }


        EcomRoom room = this.iEcomRoomRepository.getRoomById(id);
        if(room != null) {
            List<EcomBookingRoom> existingBookings = room.getBookings();

            boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
            if (roomIsAvailable) {
                room.setBooked(true);
                room.addBooking(bookingRequest);
                iEcomBookingRepository.save(bookingRequest);
                log.info("Booking saved successfully. Confirmation code: {}", bookingRequest.getBookingConfirmationCode());
            } else {
                log.info("Room not available for selected dates. Room ID: {}", room.getId());
                throw new InvalidEcomBookingRequestException("Sorry, this room is not available for the selected dates;");
            }

        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public EcomBookingRoom findByBookingConfirmationCode(String confirmationCode) {
        log.info("Finding: findByBookingConfirmationCode ...");
        return this.iEcomBookingRepository.findByBookingConfirmationCode(confirmationCode);
    }


    @Override
    public List<EcomBookingRoom> getAllBookingsByRoomId(Long id) {
        log.info("Getting: getAllBookingsByRoomId ...");
        return iEcomBookingRepository.findByBookingId(id);
    }

//    private boolean roomIsAvailable(EcomBookingRoom bookingRequest, List<EcomBookingRoom> existingBookings){
//
//        return existingBookings
//                .stream()
//                .noneMatch(existingBooking
//                        -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
//
//                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
//
//                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
//                            && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
//
//                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
//                            && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
//
//                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
//                            && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
//
//                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
//                            && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
//
//                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
//                            && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
//                );
//
//
//    }


    private boolean roomIsAvailable(EcomBookingRoom bookingRequest, List<EcomBookingRoom> existingBookings) {
        return existingBookings.stream().noneMatch(existingBooking ->
                isSameDate(bookingRequest.getCheckInDate(), existingBooking.getCheckInDate()) ||
                        isBefore(bookingRequest.getCheckOutDate(), existingBooking.getCheckOutDate()) ||
                        isOverlap(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(),
                                existingBooking.getCheckInDate(), existingBooking.getCheckOutDate()));
    }

    private boolean isSameDate(LocalDate date1, LocalDate date2) {
        return date1.equals(date2);
    }

    private boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    private boolean isOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return (start1.isAfter(start2) && start1.isBefore(end2)) ||
                (end1.isAfter(start2) && end1.isBefore(end2)) ||
                (start1.isEqual(start2) && end1.isEqual(end2));
    }



}