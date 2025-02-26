package com.practice.QLTV.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PostCommentDTO {
    @NotBlank(message = "Content cannot be blank")
    String content;

    @NotNull(message = "User ID cannot be null")
    Integer userId;

    @NotNull(message = "Post ID cannot be null")
    Integer postId;

    Integer parentId;
}