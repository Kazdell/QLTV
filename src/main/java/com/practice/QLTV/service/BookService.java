package com.practice.QLTV.service;

import com.practice.QLTV.dto.BookDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    ApiResponse<List<BookDTO>> addBook(BookDTO bookDTO);

    ApiResponse<List<BookDTO>> addMultiBook(MultipartFile multipartFile) throws IOException;

    ApiResponse<List<BookDTO>> findAllBooks();

    ApiResponse<List<BookDTO>> findBookByTitle(String title);

    ApiResponse<String> exportBooksToExcel() throws IOException;

    ApiResponse<List<BookDTO>> deleteBookByTitle(String title);
}