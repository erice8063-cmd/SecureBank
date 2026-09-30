package org.example.dbproject.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.dbproject.entity.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role = RoleType.CUSTOMER;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );
    }
}