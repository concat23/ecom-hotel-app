package dev.concat.vab.ecomhotelappbackend.service;

import dev.concat.vab.ecomhotelappbackend.model.EcomRoom;
import dev.concat.vab.ecomhotelappbackend.model.EcomService;

import java.util.List;

public interface IEcomServiceService {
    EcomService saveService(String name, String description);
    List<EcomService> serviceList();
}
