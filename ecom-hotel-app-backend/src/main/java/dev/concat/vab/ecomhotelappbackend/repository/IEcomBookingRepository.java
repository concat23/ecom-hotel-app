package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomBookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IEcomBookingRepository extends JpaRepository<EcomBookingRoom,Long> {

    EcomBookingRoom findByBookingConfirmationCode(String confirmationCode);

    @Query("SELECT b FROM EcomBookingRoom b WHERE b.id = :id")
    List<EcomBookingRoom> findByBookingId(Long id);

}
