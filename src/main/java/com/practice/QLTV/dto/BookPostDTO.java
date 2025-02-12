package com.practice.QLTV.dto;

import lombok.Data;

@Data
public class BookPostDTO {
    private String title;
    private String content;
    private Integer bookId;
    private Integer userId;
}

