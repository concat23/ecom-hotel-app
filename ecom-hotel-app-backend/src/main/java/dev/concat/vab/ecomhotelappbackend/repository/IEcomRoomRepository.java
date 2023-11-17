package dev.concat.vab.ecomhotelappbackend.repository;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IEcomRoomRepository extends JpaRepository<EcomRoom, Long> {

    @Query("SELECT DISTINCT er.roomType FROM EcomRoom er")
    List<String> findDistinctRoomTypes();
}
