package dev.concat.vab.ecomhotelappbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "ecom_tokens")
public class EcomToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "access_token", nullable = false, length = 512)
    private String accessToken;

    @Column(name = "expiration_time")
    private Date expirationTime;
    private boolean deleteToken;
    @OneToOne
    @JoinColumn(name = "ecom_user_id", referencedColumnName = "id")
    @JsonBackReference
    private EcomUser ecomUser;
}
