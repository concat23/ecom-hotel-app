package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.exception.InternalServerException;
import dev.concat.vab.ecomhotelappbackend.exception.ResourceNotFoundException;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomRoomRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomRoomService;
import dev.concat.vab.ecomhotelappbackend.utils.CustomOptional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EcomRoomServiceImpl implements IEcomRoomService {

    private static final Logger log = LoggerFactory.getLogger(EcomRoomServiceImpl.class);

    private final IEcomRoomRepository iEcomRoomRepository;
    @Override
    public EcomRoom addNewRoom(MultipartFile photo,String roomCode ,String roomType, BigDecimal roomPrice) {

        try {

            log.info("Checking: EcomRoomServiceImpl:class > addNewRoom:mdethod ");
            log.info("Adding room ...");

            EcomRoom room = new EcomRoom();
            if(!photo.isEmpty()){
                byte[] photoBytes = photo.getBytes();
                Blob photoBlob = new SerialBlob(photoBytes);
                room.setPhoto(photoBlob);
            }
            room.setRoomCode(roomCode);
            room.setRoomType(roomType);
            room.setBooked(false);
            room.setRoomPrice(roomPrice);
            EcomRoom result = iEcomRoomRepository.save(room);
            log.info("Added success. Room ID: {}",result.getId());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<EcomRoom> roomList() {
        List<EcomRoom> rooms = this.iEcomRoomRepository.findAll();
        return rooms;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return this.iEcomRoomRepository.findDistinctRoomTypes();
    }



    @Override
    public byte[] getRoomPhotoByRoomId(Long id) {
        CustomOptional<EcomRoom> theRoom = CustomOptional.ofNullable(this.iEcomRoomRepository.findById(id).orElse(null));
        if(theRoom.isEmpty()){
            throw new ResourceNotFoundException("Sorry , Room not found!");
        }

        Blob photoBlob = theRoom.get().getPhoto();
        if(photoBlob != null){
            try {
                return photoBlob.getBytes(1, (int)photoBlob.length());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public EcomRoom updateRoom(Long roomId, String roomCode, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        EcomRoom room = this.iEcomRoomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException(" Room not found !"));
        if(roomCode != null){
            room.setRoomCode(roomCode);
        }
        if(roomType != null){
            room.setRoomType(roomType);
        }
        if(roomPrice != null){
            room.setRoomPrice(roomPrice);
        }
        if(photoBytes != null && photoBytes.length > 0){
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            }catch (SQLException ex){
                throw new InternalServerException("Error updating room");
            }
        }
        return this.iEcomRoomRepository.save(room);
    }

    @Override
    public EcomRoom getEcomRoomId(Long id) {
        log.info("Room ID: {}",id);
        return this.iEcomRoomRepository.getRoomById(id);
    }

    @Override
    public void deleteUpdateBackupAndRestoreRoom(Long id) {
        EcomRoom ecomRoom = this.iEcomRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));;
        ecomRoom.setDeleted(LocalDateTime.now());
        this.iEcomRoomRepository.save(ecomRoom);
    }

    @Override
    public void deleteDropRoom(Long id) {
        this.iEcomRoomRepository.deleteById(id);
    }


}
