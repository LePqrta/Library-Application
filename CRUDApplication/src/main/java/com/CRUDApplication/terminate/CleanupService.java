package com.CRUDApplication.terminate;

import com.CRUDApplication.exception.user.SuperAdminNotFoundException;
import com.CRUDApplication.repo.UserRepo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import static java.lang.System.*;

@Component
public class CleanupService implements DisposableBean {
    private final UserRepo userRepo;

    public CleanupService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void destroy() throws SuperAdminNotFoundException {
        if (userRepo.findByUsername("Super Admin").isPresent()) {
            try {
                userRepo.delete(userRepo.findByUsername("Super Admin").get());
                out.println("Super Admin is deleted\nTerminating");
            } catch (Exception e) {
                throw new SuperAdminNotFoundException("Super Admin is not present in DB");
            }
        } else {
            out.println("Super Admin is not present in DB \nTerminating.");
            throw new SuperAdminNotFoundException("Super Admin not found. Terminating process.");
        }
    }
}


