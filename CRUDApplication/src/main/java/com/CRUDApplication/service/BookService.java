package com.CRUDApplication.service;
import com.CRUDApplication.exception.book.AuthorNotFoundException;
import com.CRUDApplication.exception.book.BookNotFoundException;
import com.CRUDApplication.model.Book;
import com.CRUDApplication.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;



    public Book saveOrUpdateBook(Book book) {
        Optional<Book> existingBook = findByTitle(book.getTitle());
        if(existingBook.isPresent()){
            Book bookToUpdate=existingBook.get();
            bookToUpdate.setBookCount(bookToUpdate.getBookCount()+1);
            bookToUpdate.setAvailableCount(bookToUpdate.getAvailableCount()+1);
            bookToUpdate.setLastAdded(book.getLastAdded());
            return bookRepo.save(bookToUpdate);
        }else {
            book.setFirstAddDate(book.getLastAdded());
            return bookRepo.save(book);
        }

    }
    public List<Book> getAllBooks() throws BookNotFoundException {
        if(bookRepo.findAll().isEmpty()){
            throw new BookNotFoundException("Book not found");
        }
        return bookRepo.findAll();
    }
    public Optional<Book> getBookById(long id) throws BookNotFoundException {
        if(bookRepo.findById(id).isPresent()){
            return bookRepo.findById(id);
        }
        throw new BookNotFoundException("Book not found");
    }
    public void deleteBookById(Long id) throws BookNotFoundException {
        if (bookRepo.existsById(id)) {
            bookRepo.deleteById(id);
            return;
        }
        throw new BookNotFoundException("Book not found");
    }
    public Optional<Book> findByTitle(String title) throws BookNotFoundException {
        return bookRepo.findByTitle(title);
    }
    public List<Book> findByAuthor(String author) throws AuthorNotFoundException {
        if (bookRepo.findByAuthor(author).isEmpty()){
            throw new AuthorNotFoundException();
        }
        return bookRepo.findByAuthor(author);
    }
    public List<Book> findAllByOrderByBookCountDesc() throws BookNotFoundException{
        if (bookRepo.findAllByOrderByBookCountDesc().isEmpty()){
            throw new BookNotFoundException("No Books Found");
        }
        return bookRepo.findAllByOrderByBookCountDesc();
    }
    public List<Book> findAllByOrderByBookCountAsc() throws BookNotFoundException{
        if (bookRepo.findAllByOrderByBookCountAsc().isEmpty()){
            throw new BookNotFoundException("No Books Found");
        }
        return bookRepo.findAllByOrderByBookCountDesc();
    }
    public List<Book> findAllOrderByPublishDateAsc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByPublishDateAsc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByPublishDateAsc();
    }
    public List<Book> findAllOrderByPublishDateDesc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByPublishDateDesc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByPublishDateDesc();
    }
    public List<Book> findAllOrderByFirstAddDateAsc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByFirstAddDateAsc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByPublishDateAsc();
    }
    public List<Book> findAllOrderByFirstAddDateDesc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByFirstAddDateDesc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByPublishDateDesc();
    }
    public List<Book> findAllOrderByAvailableCountAsc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByAvailableCountAsc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByAvailableCountAsc();
    }
    public List<Book> findAllOrderByAvailableCountDesc() throws BookNotFoundException{
        if(bookRepo.findAllOrderByAvailableCountDesc().isEmpty()){
            throw new BookNotFoundException("No books found");
        }
        return bookRepo.findAllOrderByAvailableCountDesc();
    }
}
