package dev.concat.vab.ecomhotelappbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.concat.vab.ecomhotelappbackend.enumeration.TokenType;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    @Column(name = "expiration_time")
    private Date expirationTime;
    private boolean deleteToken;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecom_user_id", referencedColumnName = "id")
    @JsonBackReference
    private EcomUser ecomUser;


    public EcomToken() {
        // Empty constructor
    }

    private EcomToken(Builder builder) {
        this.ecomUser = builder.ecomUser;
        this.accessToken = builder.accessToken;
        this.expired = builder.expired;
        this.revoked = builder.revoked;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private EcomUser ecomUser;
        private String accessToken;
        private boolean expired;
        private boolean revoked;

        public Builder ecomUser(EcomUser ecomUser) {
            this.ecomUser = ecomUser;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public Builder revoked(boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public EcomToken build() {
            return new EcomToken(this);
        }
    }
}
