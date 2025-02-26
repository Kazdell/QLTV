package com.practice.QLTV.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookPostDTO {
    Integer id;

    @NotBlank(message = "Title cannot be blank")
    String title;

    @NotBlank(message = "Content cannot be blank")
    String content;

    @NotNull(message = "Book ID cannot be null")
    Integer bookId;

    @NotNull(message = "User ID cannot be null")
    Integer userId;
}