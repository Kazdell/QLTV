package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.Book;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.BookRepository;
import com.practice.QLTV.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ApiResponse<List<BookDTO>> addBook(BookDTO bookDTO) {
        if (bookRepository.findByTitle(bookDTO.getTitle()).isPresent()) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Book with title " + bookDTO.getTitle() + " already exists");
        }
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .totalQuantity(bookDTO.getTotalQuantity())
                .borrowQuantity(0)
                .isActive(true)
                .isDeleted(false)
                .build();
        bookRepository.save(book);
        List<BookDTO> allBooks = findAllBooksAsDTO();
        return ApiResponse.<List<BookDTO>>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message("Book created successfully")
                .result(allBooks)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<List<BookDTO>> addMultiBook(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<Book> books = new ArrayList<>();
        int rowNum = 0;

        for (Row row : sheet) {
            if (rowNum++ == 0) continue; // Skip header
            String title = row.getCell(0).getStringCellValue();
            if (bookRepository.findByTitle(title).isPresent()) {
                continue; // Skip duplicates
            }
            Book book = Book.builder()
                    .title(title)
                    .author(row.getCell(1).getStringCellValue())
                    .totalQuantity((int) row.getCell(2).getNumericCellValue())
                    .borrowQuantity(0)
                    .isActive(true)
                    .isDeleted(false)
                    .build();
            books.add(book);
        }
        workbook.close();
        bookRepository.saveAll(books);
        List<BookDTO> allBooks = findAllBooksAsDTO();
        return ApiResponse.<List<BookDTO>>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message("Books created successfully")
                .result(allBooks)
                .build();
    }

    @Override
    public ApiResponse<List<BookDTO>> findAllBooks() {
        List<BookDTO> books = findAllBooksAsDTO();
        return ApiResponse.<List<BookDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(books)
                .build();
    }

    @Override
    public ApiResponse<List<BookDTO>> findBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        BookDTO bookDTO = toBookDTO(book);
        return ApiResponse.<List<BookDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(List.of(bookDTO))
                .build();
    }

    @Override
    public ApiResponse<String> exportBooksToExcel() throws IOException {
        List<Book> books = bookRepository.findAll();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Books");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id");
            headerRow.createCell(1).setCellValue("Title");
            headerRow.createCell(2).setCellValue("Author");
            headerRow.createCell(3).setCellValue("Total Quantity");
            headerRow.createCell(4).setCellValue("Borrow Quantity");
            int rowNum = 1;
            for (Book book : books) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(book.getId());
                row.createCell(1).setCellValue(book.getTitle());
                row.createCell(2).setCellValue(book.getAuthor());
                row.createCell(3).setCellValue(book.getTotalQuantity());
                row.createCell(4).setCellValue(book.getBorrowQuantity());
            }
            File file = new File("books.xlsx");
            try (OutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
            return ApiResponse.<String>builder()
                    .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                    .message("Books exported successfully")
                    .result(file.getAbsolutePath())
                    .build();
        }
    }

    @Override
    @Transactional
    public ApiResponse<List<BookDTO>> deleteBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        bookRepository.delete(book);
        List<BookDTO> allBooks = findAllBooksAsDTO();
        return ApiResponse.<List<BookDTO>>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Book deleted successfully")
                .result(allBooks)
                .build();
    }

    private List<BookDTO> findAllBooksAsDTO() {
        return bookRepository.findAll().stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO toBookDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setTotalQuantity(book.getTotalQuantity());
        dto.setBorrowQuantity(book.getBorrowQuantity());
        dto.setIsActive(book.getIsActive());
        dto.setIsDeleted(book.getIsDeleted());
        return dto;
    }
}