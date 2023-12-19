package dev.concat.vab.ecomhotelappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ecom_users")
public class EcomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String accessToken;
    private String refreshToken;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<EcomRole> ecomRoles = new ArrayList<>();

}
