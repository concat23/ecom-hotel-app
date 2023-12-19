package dev.concat.vab.ecomhotelappbackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ecom_roles")
public class EcomRole {
    @Id
    private Long id;
    private String name;
    private String code;
    private String permission;
}
