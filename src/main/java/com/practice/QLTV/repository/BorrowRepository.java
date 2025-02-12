package com.practice.QLTV.repository;

import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.entity.BorrowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    List<Borrow> findByUserId(Integer userId);

    List<BorrowDetail> findBorrowDetailsByBorrowId(Integer borrowId);

    @Modifying
    @Query("INSERT INTO BorrowDetail (borrowId, bookId, quantity) VALUES (?1.borrowId, ?1.bookId, ?1.quantity)")
    void saveAll(List<BorrowDetail> borrowDetails);
}
