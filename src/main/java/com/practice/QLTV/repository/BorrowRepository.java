package com.practice.QLTV.repository;

import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.entity.BorrowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    List<Borrow> findByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO borrow_detail (borrow_id, book_id, quantity) VALUES (:borrowId, :bookId, :quantity)", nativeQuery = true)
    void insertBorrowDetail(Integer borrowId, Integer bookId, Integer quantity);

    @Query("SELECT bd FROM BorrowDetail bd WHERE bd.borrowId = :borrowId")
    List<BorrowDetail> findBorrowDetailsByBorrowId(Integer borrowId);
}
