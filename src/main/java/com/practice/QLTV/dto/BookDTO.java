package com.practice.QLTV.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookDTO {
    Integer id; // Added for retrieval

    @NotBlank(message = "Title cannot be blank")
    String title;

    @NotBlank(message = "Author cannot be blank")
    String author;

    @NotNull(message = "Total quantity cannot be null")
    @Min(value = 1, message = "Total quantity must be at least 1")
    Integer totalQuantity;

    Integer borrowQuantity;
    Boolean isActive;
    Boolean isDeleted;
}