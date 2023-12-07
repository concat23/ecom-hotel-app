package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IEcomBookedRoomRepository extends JpaRepository<EcomBookingRoom,Long> {

    EcomBookingRoom findByBookingConfirmationCode(String confirmationCode);

    List<EcomBookingRoom> findByRoomId(Long id);

}
