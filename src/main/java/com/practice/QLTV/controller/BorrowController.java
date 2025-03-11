package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.BorrowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<BorrowDTO>> createBorrow(@Valid @RequestBody BorrowDTO borrowDTO, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.createBorrow(borrowDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @PutMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<BorrowDTO>> updateBorrow(@PathVariable Integer borrowId, @Valid @RequestBody BorrowDTO borrowDTO, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.updateBorrow(borrowId, borrowDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @DeleteMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<Void>> deleteBorrow(@PathVariable Integer borrowId, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.deleteBorrow(borrowId));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/{borrowId}/return")
    public ResponseEntity<ApiResponse<BorrowDTO>> returnBorrow(@PathVariable Integer borrowId, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.returnBorrow(borrowId));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BorrowDTO>>> getAllBorrows(HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{borrowId}")
    public ResponseEntity<ApiResponse<BorrowDTO>> getBorrowById(@PathVariable Integer borrowId, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.getBorrowById(borrowId));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BorrowDTO>>> getBorrowsByUserId(@PathVariable Integer userId, HttpServletRequest request) {
        return ResponseEntity.ok(borrowService.getBorrowsByUserId(userId));
    }
}