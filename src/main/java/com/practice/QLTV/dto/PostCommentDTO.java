package com.practice.QLTV.dto;

import lombok.Data;

@Data
public class PostCommentDTO {
    private String content;
    private Integer userId;
    private Integer postId;
    private Integer parentId;
}
