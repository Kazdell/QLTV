package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.entity.BookPost;
import com.practice.QLTV.entity.PostComment;
import com.practice.QLTV.entity.PostLike;
import com.practice.QLTV.repository.PostRepository;
import com.practice.QLTV.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    @Override
    public BookPost createPost(BookPostDTO postDTO) {
        BookPost post = BookPost.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .bookId(postDTO.getBookId())
                .userId(postDTO.getUserId())
                .build();
        return postRepository.save(post);
    }

    @Override
    public BookPost updatePost(Integer postId, BookPostDTO postDTO) {
        Optional<BookPost> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            BookPost post = existingPost.get();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            return postRepository.save(post);
        }
        throw new RuntimeException("Post not found");
    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<BookPost> findPostByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

    @Override
    @Transactional
    public void likePost(PostLikeDTO postLikeDTO) {
        Optional<Boolean> currentStatus = postRepository.findLikeStatus(postLikeDTO.getPostId(), postLikeDTO.getUserId());
        if (currentStatus.isPresent()) {
            // Toggle like/unlike
            postRepository.toggleLike(postLikeDTO.getPostId(), postLikeDTO.getUserId());
        } else {
            // First-time like
            postRepository.savePostLike(postLikeDTO.getUserId(), postLikeDTO.getPostId(), true);
        }
    }

    @Override
    @Transactional
    public PostComment commentOnPost(PostCommentDTO postCommentDTO) {
        postRepository.savePostComment(
                postCommentDTO.getContent(),
                postCommentDTO.getUserId(),
                postCommentDTO.getPostId(),
                postCommentDTO.getParentId() // Reply comment
        );
        return PostComment.builder()
                .content(postCommentDTO.getContent())
                .userId(postCommentDTO.getUserId())
                .postId(postCommentDTO.getPostId())
                .parentId(postCommentDTO.getParentId())
                .build();
    }


    @Override
    public List<PostComment> getCommentsByPostId(Integer postId) {
        return postRepository.findCommentsByPostId(postId);
    }


}
