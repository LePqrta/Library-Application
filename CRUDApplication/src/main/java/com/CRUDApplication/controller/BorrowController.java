package com.CRUDApplication.controller;


import com.CRUDApplication.service.BorrowService;
import com.CRUDApplication.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    //BORROW BOOK
    @PostMapping("/borrow/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> borrow(@PathVariable int id) {
            Book borrowedBook = borrowService.borrowBookById(id);
                return ResponseEntity.ok("Book borrowed successfully. Book Name: " +
                        borrowedBook.getTitle() + " \nBorrow ID: " + borrowService.getBorrowId());

    }

    //RETURN BOOK
    @PutMapping("/return/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> returnBorrow(@PathVariable int id) {
            borrowService.returnBook(id);
            return ResponseEntity.ok("Book returned successfully.");
    }
}
