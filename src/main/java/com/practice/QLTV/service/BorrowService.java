package com.practice.QLTV.service;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.entity.Borrow;

import java.util.List;

public interface BorrowService {
    Borrow createBorrow(BorrowDTO borrowDTO);

    Borrow updateBorrow(Integer borrowId, BorrowDTO borrowDTO);

    void deleteBorrow(Integer borrowId);

    Borrow returnBorrow(Integer borrowId);

    List<Borrow> getAllBorrows();

    Borrow getBorrowById(Integer borrowId);

    List<Borrow> getBorrowsByUserId(Integer userId);
}
