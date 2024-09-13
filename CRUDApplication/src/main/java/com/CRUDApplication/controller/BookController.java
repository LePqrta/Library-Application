package com.CRUDApplication.controller;

import com.CRUDApplication.exception.book.AuthorNotFoundException;
import com.CRUDApplication.exception.book.BookNotFoundException;
import com.CRUDApplication.service.BookService;
import com.CRUDApplication.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    //GET ALL THE BOOKS
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);

        } catch (BookNotFoundException ex) {
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    //ADD BOOK
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.saveOrUpdateBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    //GET BY TITLE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
           try {
               bookService.findByTitle(title);

               if (bookService.findByTitle(title).isPresent()) {
                   return ResponseEntity.ok(bookService.findByTitle(title).get());
               }
           }
           catch (BookNotFoundException ex) {
               return  ResponseEntity.status(404).header("Error-Message", "Book can not be found")
                       .build();
           }
            return  ResponseEntity.status(500).header("Error-Message", "Unknown error").build();

    }
    //GET BY AUTHOR(LIST)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author) {
        try {
            List<Book> authorsBooks = bookService.findByAuthor(author);
            return ResponseEntity.ok(authorsBooks);

        } catch (AuthorNotFoundException ex) {
            return ResponseEntity.status(404)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }
    //GET BOOKS ORDERED BY NUMBER
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ordered-desc")
    public ResponseEntity<List<Book>> getOrderedBooksDesc() {
        try {
                List<Book> orderedBookList = bookService.findAllByOrderByBookCountDesc();
                return new ResponseEntity<>(orderedBookList, HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ordered-asc")
    public ResponseEntity<List<Book>> getOrderedBooksAsc() {
        try {
            List<Book> orderedBookList = bookService.findAllByOrderByBookCountAsc();
            return new ResponseEntity<>(orderedBookList, HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/publish-date-asc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByPublishDateAsc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByPublishDateAsc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/publish-date-desc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByPublishDateDesc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByPublishDateDesc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/first-add-date-asc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByFirstAddDateAsc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByFirstAddDateAsc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/first-add-date-desc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByFirstAddDateDesc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByFirstAddDateDesc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/available-count-asc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByAvailableCountAsc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByAvailableCountAsc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }
    @GetMapping("/available-count-desc")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Book>> booksOrderedByAvailableCountDesc(){
        try {
            return new ResponseEntity<>(bookService.findAllOrderByAvailableCountDesc(),HttpStatus.OK);
        }catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database")
                    .build();
        }
    }

    //GET BOOK BY ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            return bookService.getBookById(id)
                    .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (BookNotFoundException exception){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database with id "+id)
                    .build();
        }
    }
    //DELETE BOOK BY ID
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (BookNotFoundException ex){
            return ResponseEntity.status(404)
                    .header("Error-Message", "No books found in the database with id "+id)
                    .build();
        }
    }

}
