package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.BorrowService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<BorrowDTO>> createBorrow(@Valid @RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.createBorrow(borrowDTO));
    }

    @PutMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<BorrowDTO>> updateBorrow(@PathVariable Integer borrowId, @Valid @RequestBody BorrowDTO borrowDTO) {
        return ResponseEntity.ok(borrowService.updateBorrow(borrowId, borrowDTO));
    }

    @DeleteMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<Void>> deleteBorrow(@PathVariable Integer borrowId) {
        return ResponseEntity.ok(borrowService.deleteBorrow(borrowId));
    }

    @PostMapping("/{borrowId}/return")
    public ResponseEntity<ApiResponse<BorrowDTO>> returnBorrow(@PathVariable Integer borrowId) {
        return ResponseEntity.ok(borrowService.returnBorrow(borrowId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BorrowDTO>>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<BorrowDTO>> getBorrowById(@PathVariable Integer borrowId) {
        return ResponseEntity.ok(borrowService.getBorrowById(borrowId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BorrowDTO>>> getBorrowsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(borrowService.getBorrowsByUserId(userId));
    }
}