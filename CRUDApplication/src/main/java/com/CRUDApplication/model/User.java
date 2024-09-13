package com.CRUDApplication.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Entity
@Table(name = "Users")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private Boolean enabled=true;
    private Boolean premium=false;
    private int borrowRights;


    @Setter
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Roles role;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role == null ? List.of() : List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }
    @Override
    public boolean isEnabled(){
        return enabled;
    }
    public void decrementBorrowRights(){
        borrowRights--;
    }
    public void incrementBorrowRights(){
        borrowRights++;
    }

}
