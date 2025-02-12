package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.BorrowDetailDTO;
import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.repository.BorrowRepository;
import com.practice.QLTV.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImp implements BorrowService {

    private final BorrowRepository borrowRepository;

    @Transactional
    @Override
    public Borrow createBorrow(BorrowDTO borrowDTO) {
        Borrow borrow = Borrow.builder()
                .userId(borrowDTO.getUserId())
                .borrowDate(LocalDate.now())
                .returnDate(borrowDTO.getReturnDate())
                .status("ACTIVE")
                .totalQuantity(borrowDTO.getTotalQuantity())
                .build();
        borrow = borrowRepository.save(borrow);

        // Insert BorrowDetail records using the same repository
        for (BorrowDetailDTO detailDTO : borrowDTO.getBorrowDetails()) {
            borrowRepository.insertBorrowDetail(
                    borrow.getId(), detailDTO.getBookId(), detailDTO.getQuantity()
            );
        }
        return borrow;
    }

    @Transactional
    @Override
    public Borrow updateBorrow(Integer borrowId, BorrowDTO borrowDTO) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        borrow.setReturnDate(borrowDTO.getReturnDate());
        borrow.setStatus(borrowDTO.getStatus());
        return borrowRepository.save(borrow);
    }

    @Transactional
    @Override
    public void deleteBorrow(Integer borrowId) {
        borrowRepository.deleteById(borrowId);
    }

    @Transactional
    @Override
    public Borrow returnBorrow(Integer borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));
        borrow.setStatus("COMPLETED");
        return borrowRepository.save(borrow);
    }

    @Override
    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow getBorrowById(Integer borrowId) {
        return borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));
    }

    @Override
    public List<Borrow> getBorrowsByUserId(Integer userId) {
        return borrowRepository.findByUserId(userId);
    }
}
