package com.CRUDApplication.repo;

import com.CRUDApplication.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.enabled=true AND u.username!='Super Admin'")
    List<User> findAllDisabled();
    @Query("select u from User u where u.enabled=false AND u.username!='Super Admin'")
    List<User> findAllEnabled();
    @Query("select u from User u where u.premium=true AND u.username!='Super Admin'")
    List<User> findAllPremium();
    @Query("select u from User u where u.premium=false AND u.username!='Super Admin'")
    List<User> findAllNonPremium();
    @Override
    @Query("select u from User u where u.username!='Super Admin'")
    @NonNull
    ArrayList<User> findAll();
}
