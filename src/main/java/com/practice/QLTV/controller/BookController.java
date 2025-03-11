package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> addBook(HttpServletRequest request, @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<BookDTO>>> addMultiBook(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(bookService.addMultiBook(file));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks(HttpServletRequest request) {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{title}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBookByTitle(HttpServletRequest request, @PathVariable String title) {
        return ResponseEntity.ok(bookService.findBookByTitle(title));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/export")
    public ResponseEntity<ApiResponse<String>> exportBooksToExcel(HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(bookService.exportBooksToExcel());
    }

    @PreAuthorize("fileRole(#request)")
    @DeleteMapping("/{title}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> deleteBookByTitle(HttpServletRequest request, @PathVariable String title) {
        return ResponseEntity.ok(bookService.deleteBookByTitle(title));
    }
}