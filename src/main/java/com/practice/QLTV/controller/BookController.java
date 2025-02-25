package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> addBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<BookDTO>>> addMultiBook(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(bookService.addMultiBook(file));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{title}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findBookByTitle(title));
    }

    @GetMapping("/export")
    public ResponseEntity<ApiResponse<String>> exportBooksToExcel() throws IOException {
        return ResponseEntity.ok(bookService.exportBooksToExcel());
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> deleteBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.deleteBookByTitle(title));
    }
}