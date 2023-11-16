package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IEcomRoomService {
    EcomRoom addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);

    List<EcomRoom> roomList();

    List<String> getAllRoomTypes();

    byte[] getRoomPhotoByRoomId(Long id);

    void deleteUpdateBackupAndRestoreRoom(Long id);

    void deleteDropRoom(Long id);
}
