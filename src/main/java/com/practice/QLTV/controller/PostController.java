package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<BookPostDTO>> createPost(HttpServletRequest request, @Valid @RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<BookPostDTO>> updatePost(HttpServletRequest request, @PathVariable Integer postId, @Valid @RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, postDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(HttpServletRequest request, @PathVariable Integer postId) {
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookPostDTO>>> findPostByTitle(HttpServletRequest request, @RequestParam String title) {
        return ResponseEntity.ok(postService.findPostByTitle(title));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/like")
    public ResponseEntity<ApiResponse<Void>> likePost(HttpServletRequest request, @Valid @RequestBody PostLikeDTO postLikeDTO) {
        return ResponseEntity.ok(postService.likePost(postLikeDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/comment")
    public ResponseEntity<ApiResponse<PostCommentDTO>> commentOnPost(HttpServletRequest request, @Valid @RequestBody PostCommentDTO postCommentDTO) {
        return ResponseEntity.ok(postService.commentOnPost(postCommentDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<PostCommentDTO>>> getCommentsByPostId(HttpServletRequest request, @PathVariable Integer postId) {
        return ResponseEntity.ok(postService.getCommentsByPostId(postId));
    }
}