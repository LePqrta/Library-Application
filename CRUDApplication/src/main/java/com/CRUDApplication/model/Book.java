package com.CRUDApplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "Books", uniqueConstraints = {
        @UniqueConstraint(name = "uc_book_title", columnNames = {"title"})
})
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String author;
    private int bookCount=1;
    private int availableCount=1;
    private LocalDate publishDate;
    private LocalDateTime firstAddDate = LocalDateTime.now();
    private LocalDateTime lastAdded=LocalDateTime.now();
}

