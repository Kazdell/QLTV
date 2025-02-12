package com.practice.QLTV.controller;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.entity.BookPost;
import com.practice.QLTV.entity.PostComment;
import com.practice.QLTV.service.PostService;
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
    public ResponseEntity<BookPost> createPost(@RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<BookPost> updatePost(@PathVariable Integer postId, @RequestBody BookPostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(postId, postDTO));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookPost>> findPostByTitle(@RequestParam String title) {
        return ResponseEntity.ok(postService.findPostByTitle(title));
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody PostLikeDTO postLikeDTO) {
        postService.likePost(postLikeDTO);
        return ResponseEntity.ok("Like status toggled");
    }

    @PostMapping("/comment")
    public ResponseEntity<PostComment> commentOnPost(@RequestBody PostCommentDTO postCommentDTO) {
        return ResponseEntity.ok(postService.commentOnPost(postCommentDTO));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<PostComment>> getCommentsByPostId(@PathVariable Integer postId) {
        return ResponseEntity.ok(postService.getCommentsByPostId(postId));
    }


}
