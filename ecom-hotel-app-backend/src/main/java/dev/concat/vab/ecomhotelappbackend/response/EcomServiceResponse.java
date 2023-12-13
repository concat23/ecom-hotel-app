package dev.concat.vab.ecomhotelappbackend.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EcomServiceResponse {
    private Long id;
    private String name;
    private String description;
    public EcomServiceResponse(String name,String description){
        this.name = name;
        this.description = description;
    }

    public EcomServiceResponse(Long id,String name){
        this.id = id;
        this.name = name;
    }
}
