package dev.concat.vab.ecomhotelappbackend.controller;

import dev.concat.vab.ecomhotelappbackend.exception.PhotoRetrievalException;
import dev.concat.vab.ecomhotelappbackend.model.EcomBookingRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomService;
import dev.concat.vab.ecomhotelappbackend.response.EcomBookingResponse;
import dev.concat.vab.ecomhotelappbackend.response.EcomRoomResponse;
import dev.concat.vab.ecomhotelappbackend.response.EcomServiceResponse;
import dev.concat.vab.ecomhotelappbackend.service.IEcomServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/services")
@CrossOrigin(origins = "http://localhost:5173")
public class EcomServiceController {

    private final IEcomServiceService iEcomServiceService;



    @PostMapping(path = "/add/service")
    public ResponseEntity<EcomServiceResponse> add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description){
        EcomService ecomService = this.iEcomServiceService.saveService(name,description);
        EcomServiceResponse ecomServiceResponse = new EcomServiceResponse(ecomService.getName(), ecomService.getDescription());
        return ResponseEntity.ok(ecomServiceResponse);
    }

    @GetMapping(path="/all-services")
    public ResponseEntity<List<EcomServiceResponse>> getAllServices(){
        List<EcomService> services = this.iEcomServiceService.serviceList();
        List<EcomServiceResponse> servicesRes = new ArrayList<>();

        for(EcomService service : services){

                EcomServiceResponse ecomServiceResponse = getEcomServiceResponse(service);
                servicesRes.add(ecomServiceResponse);

        }
        return ResponseEntity.ok(servicesRes);
    }


    private EcomServiceResponse getEcomServiceResponse(EcomService service){
        return new EcomServiceResponse(service.getName(),service.getDescription());

    }

}
