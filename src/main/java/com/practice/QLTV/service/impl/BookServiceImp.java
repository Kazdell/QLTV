package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.entity.Book;
import com.practice.QLTV.repository.BookRepository;
import com.practice.QLTV.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> addBook(BookDTO bookDTO) {
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .totalQuantity(bookDTO.getTotalQuantity())
                .borrowQuantity(0)
                .isActive(true)
                .isDeleted(false)
                .build();
        bookRepository.save(book);
        return findAllBooks();
    }

    @Override
    @Transactional
    public List<Book> addMultiBook(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        Row row;
        List<Book> books = new ArrayList<>();

        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Book book = Book.builder()
                    .title(row.getCell(0).getStringCellValue())
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
        return findAllBooks();
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElse(null);
        return book != null ? List.of(book) : Collections.emptyList();
    }

    @Override
    public File exportBooksToExcel() throws IOException {
        List<Book> books = bookRepository.findAll();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Books");
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
            return file;
        }
    }

    @Override
    public List<Book> deleteBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
        return findAllBooks();
    }
}

