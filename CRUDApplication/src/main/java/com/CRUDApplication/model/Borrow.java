package com.CRUDApplication.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "Borrow")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long bookId;
    private LocalDateTime borrowDate;
    private LocalDateTime lastReturnDate;
    private LocalDateTime dateOfReturn;
    private boolean isReturned;
    private long borrowerId;
}
