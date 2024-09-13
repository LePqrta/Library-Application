package com.CRUDApplication.bootstrap;

import com.CRUDApplication.enums.RoleEnum;
import com.CRUDApplication.dto.RegisterDTO;
import com.CRUDApplication.model.Roles;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.RolesRepo;
import com.CRUDApplication.repo.UserRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.System.*;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RolesRepo roleRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterDTO userDto = new RegisterDTO();
        userDto.setUsername("Super Admin");
        userDto.setPassword("SuperAdmin");
        Optional<Roles> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            out.println("Super Admin Role already exists");
            return;
        }

        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());
        user.setPremium(true);
        userRepository.save(user);
        out.println("Super Admin created");
    }
}
