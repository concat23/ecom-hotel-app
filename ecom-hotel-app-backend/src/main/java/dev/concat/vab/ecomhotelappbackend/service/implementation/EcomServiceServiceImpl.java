package dev.concat.vab.ecomhotelappbackend.service.implementation;

import dev.concat.vab.ecomhotelappbackend.model.EcomService;
import dev.concat.vab.ecomhotelappbackend.repository.IEcomServiceRepository;
import dev.concat.vab.ecomhotelappbackend.service.IEcomServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcomServiceServiceImpl implements IEcomServiceService {

    private final IEcomServiceRepository iEcomServiceRepository;
    @Override
    public EcomService saveService(String name, String description) {
        EcomService service = new EcomService();
        service.setName(name);
        service.setDescription(description);
        return this.iEcomServiceRepository.save(service);
    }

    @Override
    public List<EcomService> serviceList() {
        return this.iEcomServiceRepository.findAll();
    }


}
