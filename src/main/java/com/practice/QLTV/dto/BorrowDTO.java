package com.practice.QLTV.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BorrowDTO {
    @NotNull(message = "User ID cannot be null")
    Integer userId;

    @NotNull(message = "Borrow date cannot be null")
    @PastOrPresent(message = "Borrow date must be in the past or present")
    LocalDate borrowDate;

    @Future(message = "Return date must be in the future")
    LocalDate returnDate;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "ACTIVE|COMPLETED", message = "Status must be ACTIVE or COMPLETED")
    String status;

    @NotNull(message = "Total quantity cannot be null")
    @Min(value = 1, message = "Total quantity must be at least 1")
    Integer totalQuantity;

    @NotEmpty(message = "Borrow details cannot be empty")
    List<BorrowDetailDTO> borrowDetails;
}