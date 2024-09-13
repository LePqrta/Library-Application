package com.CRUDApplication.bootstrap;

import com.CRUDApplication.enums.RoleEnum;
import com.CRUDApplication.model.Roles;
import com.CRUDApplication.repo.RolesRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.*;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RolesRepo roleRepository;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Roles> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(out::println, () -> {
                Roles roleToCreate = new Roles();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
