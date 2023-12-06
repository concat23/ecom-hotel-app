package dev.concat.vab.ecomhotelappbackend.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;


@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ecom_accounts_admin")
public class EcomAccountAdmin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    private String name;
    @Column(unique = true)
    @NotNull
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;
    private String email;
    private Boolean isActive;
    private boolean enabled = true;
    private boolean credentialsexpired = false;
    private boolean expired = false;
    private boolean locked = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "ecom_accounts_roles_admin",
            joinColumns = @JoinColumn(name = "ecom_accountId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ecom_roleId",referencedColumnName = "id")
    )
    private Set<EcomRoleAdmin> roles;
}
