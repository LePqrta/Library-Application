package com.CRUDApplication.service;

import com.CRUDApplication.dto.RegisterDTO;
import com.CRUDApplication.enums.RoleEnum;
import com.CRUDApplication.exception.role.RoleIsNullException;
import com.CRUDApplication.exception.user.*;
import com.CRUDApplication.model.Roles;
import com.CRUDApplication.repo.RolesRepo;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;

    public void deleteUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
           throw new UserNotFoundException("User not found");
        }
            userRepo.delete(user.get());
    }
    public void addUser(RegisterDTO registerDTO) throws RoleIsNullException, UsernameIsBeingUsedException {
        Optional<Roles> role = rolesRepo.findByName(RoleEnum.USER);
        if(role.isEmpty()){
            throw new RoleIsNullException("Role is null. Check if role is in database");
        }
        if (userRepo.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new UsernameIsBeingUsedException("Username is already taken.");
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setBorrowRights(5);
        user.setPremium(false);
        user.setRole(role.get());
        userRepo.save(user);
    }

    public void disableUser(String username) throws UserNotFoundException, AlreadyDisabledException {

        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if (!userOptional.get().isEnabled()) {
            throw new AlreadyDisabledException("User is already disabled");
        }
        userOptional.get().setEnabled(false);
        userRepo.save(userOptional.get());
    }

    public void enableUser(String username) {

        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if (userOptional.get().isEnabled()) {
            throw new AlreadyEnabledException("User is already enabled");
        }
        userOptional.get().setEnabled(true);
        userRepo.save(userOptional.get());
    }

    public void createAdministrator(RegisterDTO input) throws RoleIsNullException, UsernameIsBeingUsedException {
        Optional<Roles> optionalRole = rolesRepo.findByName(RoleEnum.ADMIN);
        if(optionalRole.isEmpty()){
            throw new RoleIsNullException("Role is null. Contact an administrator.");
        }
        if (userRepo.findByUsername(input.getUsername()).isPresent()) {
            throw new UsernameIsBeingUsedException("Username is already taken.");
        }
        var user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());
        user.setCreatedAt(LocalDateTime.now());
        user.setBorrowRights(10);
        user.setPremium(true);
        userRepo.save(user);
    }
    public void makePremium(long id) throws AlreadyPremiumException, UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        if (Boolean.FALSE.equals(userOptional.get().getPremium())) {
            userOptional.get().setPremium(true);
            userOptional.get().setBorrowRights(10);
            userRepo.save(userOptional.get());
            return;
        }
        throw new AlreadyPremiumException("User is already premium");
    }
    public void takePremium(long id) throws AlreadyPremiumException, UserNotFoundException{
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        if (Boolean.TRUE.equals(userOptional.get().getPremium())) {
            userOptional.get().setPremium(false);
            userOptional.get().setBorrowRights(5);
            userRepo.save(userOptional.get());
            return;
        }
        throw new AlreadyNotPremiumException("User is already not premium");
    }
}
