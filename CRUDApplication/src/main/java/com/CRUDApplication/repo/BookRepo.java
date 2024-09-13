package com.CRUDApplication.repo;

import com.CRUDApplication.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    @Query("select b from Book b order by b.bookCount")
    List<Book> findAllByOrderByBookCountDesc();
    @Query("select b from Book b order by b.bookCount ASC ")
    List<Book> findAllByOrderByBookCountAsc();
    @Query("select b from Book b order by b.publishDate")
    List<Book> findAllOrderByPublishDateAsc();
    @Query("select b from Book b order by b.publishDate DESC")
    List<Book> findAllOrderByPublishDateDesc();
    @Query("select b from Book b order by b.firstAddDate")
    List<Book> findAllOrderByFirstAddDateAsc();
    @Query("select b from Book b order by b.firstAddDate DESC ")
    List<Book> findAllOrderByFirstAddDateDesc();
    @Query("select b from Book b order by b.availableCount")
    List<Book> findAllOrderByAvailableCountAsc();
    @Query("select b from Book b order by b.availableCount desc ")
    List<Book> findAllOrderByAvailableCountDesc();
}
