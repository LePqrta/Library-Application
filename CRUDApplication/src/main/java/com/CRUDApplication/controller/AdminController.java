package com.CRUDApplication.controller;

import com.CRUDApplication.exception.role.RoleIsNullException;
import com.CRUDApplication.exception.user.*;
import com.CRUDApplication.dto.RegisterDTO;
import com.CRUDApplication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admins")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> createAdministrator(@RequestBody RegisterDTO registerUserDto) {
        try {
            adminService.createAdministrator(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin with username "
                    +registerUserDto.getUsername()+" is created");
        }catch (UsernameIsBeingUsedException | RoleIsNullException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/disable/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> disableUser(@PathVariable String username) {
        try {
            adminService.disableUser(username);
            return new ResponseEntity<>("User " + username + " is disabled", HttpStatus.OK);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AlreadyDisabledException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/enable/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> enableUser(@PathVariable String username) {
        try {
            adminService.enableUser(username);
            return new ResponseEntity<>("User " + username + " is enabled", HttpStatus.OK);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AlreadyEnabledException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PostMapping("/create-user")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody RegisterDTO registerUserDto) {
        try{
            adminService.addUser(registerUserDto);
            return new ResponseEntity<>("User " + registerUserDto.getUsername() + " is created", HttpStatus.OK);
        }catch (RoleIsNullException | UsernameIsBeingUsedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete-user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            adminService.deleteUser(username);
            return new ResponseEntity<>("User " + username + " is deleted", HttpStatus.OK);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/premium/make/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> makePremium(@PathVariable long id) {
         try{
             adminService.makePremium(id);
             return new ResponseEntity<>("User with id " + id + " has been made premium.", HttpStatus.OK);
         }catch (AlreadyPremiumException e){
             return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
         }catch (UserNotFoundException e){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
    }
    @PostMapping("/premium/take/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> takePremium(@PathVariable long id) {
        try{
            adminService.takePremium(id);
            return new ResponseEntity<>("Premium is taken from user with id " + id , HttpStatus.OK);
        }catch (AlreadyNotPremiumException e){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
