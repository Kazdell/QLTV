package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.entity.Book;
import com.practice.QLTV.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Book>> addBook(@RequestBody BookDTO bookDTO) {
        List<Book> books = bookService.addBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(books);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Book>> addMultiBook(@RequestParam("file") MultipartFile file) {
        try {
            List<Book> books = bookService.addMultiBook(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(books);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        List<Book> books = bookService.findBookByTitle(title);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportBooksToExcel() {
        try {
            File file = bookService.exportBooksToExcel();
            return ResponseEntity.ok("Exported file path: " + file.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Export failed");
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<List<Book>> deleteBookByTitle(@PathVariable String title) {
        List<Book> books = bookService.deleteBookByTitle(title);
        return ResponseEntity.ok(books);
    }
}
