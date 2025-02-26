package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookPostDTO>> createPost(@Valid @RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<BookPostDTO>> updatePost(@PathVariable Integer postId, @Valid @RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, postDTO));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Integer postId) {
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookPostDTO>>> findPostByTitle(@RequestParam String title) {
        return ResponseEntity.ok(postService.findPostByTitle(title));
    }

    @PostMapping("/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@Valid @RequestBody PostLikeDTO postLikeDTO) {
        return ResponseEntity.ok(postService.likePost(postLikeDTO));
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<PostCommentDTO>> commentOnPost(@Valid @RequestBody PostCommentDTO postCommentDTO) {
        return ResponseEntity.ok(postService.commentOnPost(postCommentDTO));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<PostCommentDTO>>> getCommentsByPostId(@PathVariable Integer postId) {
        return ResponseEntity.ok(postService.getCommentsByPostId(postId));
    }
}