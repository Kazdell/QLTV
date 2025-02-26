package com.practice.QLTV.service;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.response.ApiResponse;

import java.util.List;

public interface BorrowService {
    ApiResponse<BorrowDTO> createBorrow(BorrowDTO borrowDTO);

    ApiResponse<BorrowDTO> updateBorrow(Integer borrowId, BorrowDTO borrowDTO);

    ApiResponse<Void> deleteBorrow(Integer borrowId);

    ApiResponse<BorrowDTO> returnBorrow(Integer borrowId);

    ApiResponse<List<BorrowDTO>> getAllBorrows();

    ApiResponse<BorrowDTO> getBorrowById(Integer borrowId);

    ApiResponse<List<BorrowDTO>> getBorrowsByUserId(Integer userId);
}