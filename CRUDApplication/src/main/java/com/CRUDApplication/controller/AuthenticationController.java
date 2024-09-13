package com.CRUDApplication.controller;

import com.CRUDApplication.dto.LoginDTO;
import com.CRUDApplication.dto.RegisterDTO;
import com.CRUDApplication.exception.role.RoleIsNullException;
import com.CRUDApplication.exception.user.PasswordException;
import com.CRUDApplication.exception.user.UsernameIsBeingUsedException;
import com.CRUDApplication.service.JwtService;
import com.CRUDApplication.model.LoginResponse;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.UserRepo;
import com.CRUDApplication.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserRepo userRepo;

    public AuthenticationController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            UserRepo userRepo
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerUserDto) {
        try {
            return authenticationService.signup(registerUserDto);
        }catch (UsernameIsBeingUsedException | RoleIsNullException | PasswordException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        if(authenticatedUser.getEnabled().equals(Boolean.FALSE)){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setIsNotEnabled("Account is not enabled");
            return ResponseEntity.ok(loginResponse);
        }
        authenticatedUser.setLastLogin(LocalDateTime.now());
        userRepo.save(authenticatedUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
