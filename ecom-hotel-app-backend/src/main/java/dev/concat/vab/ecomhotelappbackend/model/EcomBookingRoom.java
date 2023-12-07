package dev.concat.vab.ecomhotelappbackend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ecom_booking")
public class EcomBookingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name="check_out")
    private LocalDate checkOutDate;

    @Column(name = "guest_fullname")
    private String guestFullName;

    @Column(name= "guest_email")
    private String guestEmail;

    @Column(name="num_of_adults")
    private int numOfAdults;

    @Column(name="num_of_children")
    private int numOfChildren;

    @Column(name="total_num_of_guest")
    private int totalNumOfGuest;

    @Column(name = "booking_confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecom_room_id")
    private EcomRoom room;


    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest = this.numOfAdults + this.numOfChildren;
    }


    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumberOfGuest();
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
