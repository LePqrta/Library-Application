package com.CRUDApplication.service;
import com.CRUDApplication.exception.book.BookNotFoundException;
import com.CRUDApplication.exception.borrow.BorrowAlreadyReturnedException;
import com.CRUDApplication.exception.borrow.BorrowNotFoundException;
import com.CRUDApplication.exception.borrow.NotEnoughBorrowRightsException;
import com.CRUDApplication.exception.borrow.NotReturnableByCurrentUserException;
import com.CRUDApplication.exception.user.UserNotFoundException;
import com.CRUDApplication.exception.book.BookNotAvailableException;
import com.CRUDApplication.model.Book;
import com.CRUDApplication.model.Borrow;
import com.CRUDApplication.repo.BookRepo;
import com.CRUDApplication.repo.BorrowRepo;
import com.CRUDApplication.repo.UserRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BorrowService {
    private final BorrowRepo borrowRepo;
    private final BookRepo bookRepo;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    @Setter
    @Getter
    private long borrowId;
    public Book borrowBookById(long id) throws BookNotAvailableException, BookNotFoundException, NotEnoughBorrowRightsException {
        Optional<Book> existingBook = bookRepo.findById(id);
        if (existingBook.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " does not exist.");
        }

        Book book = existingBook.get();
        if (book.getAvailableCount() <= 0) {
            throw new BookNotAvailableException("Book with ID " + id + " is not available for borrowing.");
        }
        else{
            if(userRepo.findByUsername(jwtService.extractUsername(jwtService.getToken())).isEmpty()
              ||userRepo.findByUsername(jwtService.extractUsername(jwtService.getToken())).get().getBorrowRights()<=0){
                throw new NotEnoughBorrowRightsException("You have not enough borrow rights to borrow book.");
            }
            userRepo.findByUsername(jwtService.extractUsername(jwtService.getToken())).get().decrementBorrowRights();
            Borrow borrow = new Borrow();
            borrow.setBookId(id);
            borrow.setBorrowDate(LocalDateTime.now());
            borrow.setLastReturnDate(LocalDateTime.now().plusMonths(1));
            borrow.setBorrowerId(jwtService.extractId(jwtService.getToken()));
            borrowRepo.save(borrow);
            setBorrowId(borrow.getId());
            book.setAvailableCount(book.getAvailableCount() - 1);
            bookRepo.save(book);

            return book;
        }

    }
    public void returnBook(long id) throws UserNotFoundException, NotReturnableByCurrentUserException, BorrowNotFoundException, BorrowAlreadyReturnedException {
        Optional<Borrow> borrowOptional=borrowRepo.findById(id);
        if (borrowOptional.isEmpty()) {
            throw new BorrowNotFoundException("Book with ID " + id + " does not exist.");
        }
        if(borrowOptional.get().isReturned()) {
            throw new BorrowAlreadyReturnedException("This book is already returned.");
        }
            Borrow borrow=borrowOptional.get();
            Long bookId=borrow.getBookId();
            Optional<Book> bookOptional=bookRepo.findById(bookId);
            if(bookOptional.isPresent()&&borrow.getBorrowerId()==jwtService.extractId(jwtService.getToken())) {
                if(userRepo.findByUsername(jwtService.extractUsername(jwtService.getToken())).isEmpty()){
                    throw new UserNotFoundException("This user is can not be identified.");
                }
                userRepo.findByUsername(jwtService.extractUsername(jwtService.getToken())).get().incrementBorrowRights();
                Book book=bookOptional.get();
                book.setAvailableCount(book.getAvailableCount() + 1);
                borrow.setDateOfReturn(LocalDateTime.now());
                borrow.setReturned(true);
                bookRepo.save(book);
                return;
            }

        throw new NotReturnableByCurrentUserException("This user did not borrow this book, return failed.\n" +
                "Please login with the user that made the borrowing and try again");
    }
}
