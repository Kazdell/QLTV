package com.practice.QLTV.service;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.dto.response.ApiResponse;

import java.util.List;

public interface PostService {
    ApiResponse<BookPostDTO> createPost(BookPostDTO postDTO);
    ApiResponse<BookPostDTO> updatePost(Integer postId, BookPostDTO postDTO);
    ApiResponse<Void> deletePost(Integer postId);
    ApiResponse<List<BookPostDTO>> findPostByTitle(String title);
    ApiResponse<Void> likePost(PostLikeDTO postLikeDTO);
    ApiResponse<PostCommentDTO> commentOnPost(PostCommentDTO postCommentDTO);
    ApiResponse<List<PostCommentDTO>> getCommentsByPostId(Integer postId);
}