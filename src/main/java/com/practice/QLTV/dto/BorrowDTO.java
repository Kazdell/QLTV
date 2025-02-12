package com.practice.QLTV.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BorrowDTO {
    Integer userId;
    LocalDate borrowDate;
    LocalDate returnDate;
    String status;
    Integer totalQuantity;
    List<BorrowDetailDTO> borrowDetails;
}
