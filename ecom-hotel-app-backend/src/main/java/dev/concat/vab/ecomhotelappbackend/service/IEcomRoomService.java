package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IEcomRoomService {
    EcomRoom addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);

    List<EcomRoom> roomList();

    List<String> getAllRoomTypes();

    byte[] getRoomPhotoByRoomId(Long id);

    EcomRoom updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<EcomRoom> getEcomRoomId(Long roomId);

    void deleteUpdateBackupAndRestoreRoom(Long id);

    void deleteDropRoom(Long id);


}
