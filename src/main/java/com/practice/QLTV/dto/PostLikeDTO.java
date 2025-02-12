package com.practice.QLTV.dto;

import lombok.Data;

@Data
public class PostLikeDTO {
    private Integer postId;
    private Integer userId;
    private Boolean isLike;
}
