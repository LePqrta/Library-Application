package com.CRUDApplication.service;

import com.CRUDApplication.dto.LoginDTO;
import com.CRUDApplication.dto.RegisterDTO;
import com.CRUDApplication.enums.RoleEnum;
import com.CRUDApplication.exception.role.RoleIsNullException;
import com.CRUDApplication.exception.user.PasswordException;
import com.CRUDApplication.exception.user.UsernameIsBeingUsedException;
import com.CRUDApplication.model.Roles;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.RolesRepo;
import com.CRUDApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RolesRepo rolesRepo;


    public ResponseEntity<String> signup(RegisterDTO input) throws UsernameIsBeingUsedException, RoleIsNullException, PasswordException {
        Optional<Roles> optionalRole = rolesRepo.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            throw new RoleIsNullException("Role is null. Contact an administrator.");
        }
        if (userRepository.findByUsername(input.getUsername()).isPresent()){
            throw new UsernameIsBeingUsedException("Username is already in use!");
        }
        if (!input.getConfirmPassword().equals(input.getPassword())){
            throw new PasswordException("Passwords do not match. Make sure you entered you password correctly.");
        }

        if (input.getPassword().length()<8){
            throw new PasswordException("Password is invalid.\n" +"Password length should be at least 8");
        }
        if (!containsDigits(input.getPassword())){
            throw new PasswordException("Password is invalid.\n"
                    +"Password should have at least 4 digits");
        }
        if(!hasUpperLowerCase(input.getPassword())){
            throw new PasswordException("Password is invalid.\n" +
                    "Password should contain at least one lower and one uppercase letter and combined 4 letters");
        }
        if (containsWhiteSpace(input.getPassword())){
            throw new PasswordException("Password is invalid.\n"+
                    "Password should not have any whitespaces.");
        }
        if (!containsSpecialCharacter(input.getPassword())){
            throw new PasswordException("Password is invalid.\n"
                    +"Password should contain at least one special character");
        }
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setBorrowRights(5);
        user.setPremium(false);
        userRepository.save(user);
        return new ResponseEntity<>("User successfully registered!", HttpStatus.OK);
    }

    public User authenticate(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
    public boolean hasUpperLowerCase(String password){
        int countUpper=0;
        int countLower=0;

        for (char c :password.toCharArray()){
            if (Character.isUpperCase(c)){
               countUpper++;
            }
            else if (Character.isLowerCase(c)){
                countLower++;
            }
        }
        return (countUpper+countLower>=4)&&(countUpper>0)&&(countLower>0);
    }
    public boolean containsWhiteSpace(String password){
        for(char c: password.toCharArray()){
            if(Character.isWhitespace(c)){
                return true;
            }
        }
        return false;
    }
    public boolean containsSpecialCharacter(String password){
        for(char c : password.toCharArray()){
            if (!Character.isDigit(c)
                    && !Character.isLetter(c)
                    && !Character.isWhitespace(c)){
                return true;
            }
        }
        return false;
    }
    public boolean containsDigits(String password){
        int count=0;
        for(char c:password.toCharArray()){
            if(Character.isDigit(c)){
                count++;
            }
        }
        return count>=4;
    }
}



