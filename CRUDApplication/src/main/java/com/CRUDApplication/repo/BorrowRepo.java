package com.CRUDApplication.repo;
import com.CRUDApplication.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow, Long> {
    List<Borrow>findBorrowsByBorrowerId(long id);
    @Query("select b from Borrow b where b.borrowerId = ?1 and b.isReturned<> true")
    List<Borrow>findBorrowsByBorrowerIdAndReturnedNot(long id);
}
