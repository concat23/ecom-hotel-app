package dev.concat.vab.ecomhotelappbackend.model;

import dev.concat.vab.ecomhotelappbackend.utils.RandomStringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "ecom_room")
public class EcomRoom {
    private static final Logger logger = LoggerFactory.getLogger(EcomRoom.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    private LocalDateTime deleted;

    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EcomBookingRoom> bookings;

    public EcomRoom(){
        this.bookings = new ArrayList<>();
    }
    public void addBooking(EcomBookingRoom bookedRoom){
        logger.info("Checking: EcomRoom:class > addBooking:method");
        logger.info("Checking: bookings<List>:property is null ?");
        if(bookings == null){
            logger.info("Result: bookings<List>:property is null.");

            logger.info("Running: Declaring a new list...");
            bookings = new ArrayList<>();
            logger.info("Result: Declare a new list finish.");
        }

        logger.info("Running: Adding booking ...");
        bookings.add(bookedRoom);

        bookedRoom.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.generateRandomString(10);
        bookedRoom.setBookingConfirmationCode(bookingCode);
        logger.info("Result: Add booking success.");

    }
}
