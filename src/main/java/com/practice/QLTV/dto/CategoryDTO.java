package com.practice.QLTV.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class CategoryDTO {
    Integer id;

    @NotBlank(message = "Category code cannot be blank")
    String categoryCode;

    @NotBlank(message = "Category name cannot be blank")
    String categoryName;

    String description;
}