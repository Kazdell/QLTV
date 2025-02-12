package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.BorrowDetailDTO;
import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.entity.BorrowDetail;
import com.practice.QLTV.repository.BorrowRepository;
import com.practice.QLTV.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
        borrowRepository.save(borrow);

        List<BorrowDetail> borrowDetails = new ArrayList<>();
        for (BorrowDetailDTO detailDTO : borrowDTO.getBorrowDetails()) {
            BorrowDetail detail = BorrowDetail.builder()
                    .borrowId(borrow.getId())
                    .bookId(detailDTO.getBookId())
                    .quantity(detailDTO.getQuantity())
                    .build();
            borrowDetails.add(detail);
        }
        borrowRepository.saveAll(borrowDetails);
        return borrow;
    }

    @Transactional
    @Override
    public Borrow updateBorrow(Integer borrowId, BorrowDTO borrowDTO) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        borrow.setReturnDate(borrowDTO.getReturnDate());
        borrow.setStatus(borrowDTO.getStatus());
        borrowRepository.save(borrow);
        return borrow;
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
        borrowRepository.save(borrow);
        return borrow;
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
