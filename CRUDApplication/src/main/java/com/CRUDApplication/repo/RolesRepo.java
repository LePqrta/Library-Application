package com.CRUDApplication.repo;


import com.CRUDApplication.enums.RoleEnum;
import com.CRUDApplication.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(RoleEnum name);
}
