package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Borrow> createBorrow(@RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.createBorrow(borrowDTO));
    }

    @PutMapping("/{borrowId}")
    public ResponseEntity<Borrow> updateBorrow(@PathVariable Integer borrowId, @RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.updateBorrow(borrowId, borrowDTO));
    }

    @DeleteMapping("/{borrowId}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Integer borrowId) {
        borrowService.deleteBorrow(borrowId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{borrowId}/return")
    public ResponseEntity<Borrow> returnBorrow(@PathVariable Integer borrowId) {
        return ResponseEntity.ok(borrowService.returnBorrow(borrowId));
    }

    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable Integer borrowId) {
        return ResponseEntity.ok(borrowService.getBorrowById(borrowId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Borrow>> getBorrowsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(borrowService.getBorrowsByUserId(userId));
    }
}
