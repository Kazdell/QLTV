package com.practice.QLTV.service;


import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> addBook(BookDTO bookDTO);

    List<Book> addMultiBook(MultipartFile multipartFile) throws IOException;

    List<Book> findAllBooks();

    List<Book> findBookByTitle(String title);

    File exportBooksToExcel() throws IOException;

    List<Book> deleteBookByTitle(String title);
}

