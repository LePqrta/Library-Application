package com.CRUDApplication.service;

import com.CRUDApplication.exception.borrow.BorrowNotFoundException;
import com.CRUDApplication.exception.user.UserNotFoundException;
import com.CRUDApplication.model.Borrow;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.BorrowRepo;
import com.CRUDApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final BorrowRepo borrowRepo;
    private final JwtService jwtService;

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
    public List<Borrow> borrowHistory() throws UserNotFoundException, BorrowNotFoundException {
        long id=jwtService.extractId(jwtService.getToken());
        if (userRepository.existsById(id)){
            if (borrowRepo.findBorrowsByBorrowerId(id).isEmpty()){
                throw new BorrowNotFoundException("This user did not made any borrows");
            }
            return borrowRepo.findBorrowsByBorrowerId(id);
        }
        throw new UserNotFoundException("Your id has a problem, contact an admin");
    }
    public List<Borrow> notReturnedBorrowHistory() throws UserNotFoundException, BorrowNotFoundException{
        long id = jwtService.extractId(jwtService.getToken());
        if (userRepository.existsById(id)){
            if (borrowRepo.findBorrowsByBorrowerIdAndReturnedNot(id).isEmpty()){
                throw new BorrowNotFoundException("You have no waiting returns");
            }
            return borrowRepo.findBorrowsByBorrowerIdAndReturnedNot(id);
        }
        throw new UserNotFoundException("Your id has a problem, contact an admin");
    }

    public List<User> enabledUsers() throws UserNotFoundException{
            if(userRepository.findAllEnabled().isEmpty()){
                throw new UserNotFoundException("All users are disabled");
            }
            return userRepository.findAllEnabled();
    }

    public List<User> disabledUsers() throws UserNotFoundException{
        if(userRepository.findAllDisabled().isEmpty()){
            throw new UserNotFoundException("All users are enabled");
        }
        return userRepository.findAllDisabled();
    }
    public List<User> premiumUsers() throws UserNotFoundException{
        if (userRepository.findAllPremium().isEmpty()){
            throw new UserNotFoundException("No current premium users are found");
        }
        return userRepository.findAllPremium();
    }
    public List<User> nonPremiumUsers() throws UserNotFoundException{
        if (userRepository.findAllNonPremium().isEmpty()){
            throw new UserNotFoundException("No current non-premium users are found");
        }
        return userRepository.findAllNonPremium();
    }
}

