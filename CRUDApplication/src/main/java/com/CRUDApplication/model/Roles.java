package com.CRUDApplication.model;

import com.CRUDApplication.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Entity
@Table(name = "Roles")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}