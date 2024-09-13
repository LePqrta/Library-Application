package com.CRUDApplication.controller;

import com.CRUDApplication.exception.borrow.BorrowNotFoundException;
import com.CRUDApplication.exception.user.UserNotFoundException;
import com.CRUDApplication.service.UserService;
import com.CRUDApplication.model.Borrow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.CRUDApplication.model.User;
import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
    @GetMapping("/borrows")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Borrow>> borrowList(){
        try {
            return new ResponseEntity<>(userService.borrowHistory(), HttpStatus.OK);
        }catch (UserNotFoundException | BorrowNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }
    @GetMapping("/returns")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Borrow>> notReturnedBorrows(){
        try {
            return new ResponseEntity<>(userService.notReturnedBorrowHistory(), HttpStatus.OK);
        }catch (UserNotFoundException | BorrowNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/enabled")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> enabledUsers(){
        try {
            return ResponseEntity.ok(userService.enabledUsers());
        }catch (UserNotFoundException e){
            return ResponseEntity.status(404)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
    @GetMapping("/disabled")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> disabledUsers(){
        try {
            return ResponseEntity.ok(userService.disabledUsers());
        }catch (UserNotFoundException e){
            return ResponseEntity.status(404)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
    @GetMapping("/premiums")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> premiumUsers(){
        try {
            return ResponseEntity.ok(userService.premiumUsers());
        }catch (UserNotFoundException e){
            return ResponseEntity.status(404)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
    @GetMapping("/non-premiums")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> nonPremiumUsers(){
        try {
            return ResponseEntity.ok(userService.nonPremiumUsers());
        }catch (UserNotFoundException e){
            return ResponseEntity.status(404)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}

