package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.BookPost;
import com.practice.QLTV.entity.PostComment;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.BookRepository;
import com.practice.QLTV.repository.PostRepository;
import com.practice.QLTV.repository.UserRepository;
import com.practice.QLTV.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // For user validation
    private final BookRepository bookRepository; // For book validation

    @Override
    @Transactional
    public ApiResponse<BookPostDTO> createPost(BookPostDTO postDTO) {
        validatePostDTO(postDTO);
        BookPost post = BookPost.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .bookId(postDTO.getBookId())
                .userId(postDTO.getUserId())
                .build();
        post = postRepository.save(post);
        BookPostDTO result = toBookPostDTO(post);
        return ApiResponse.<BookPostDTO>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) 
                .message("Post created successfully")
                .result(result)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<BookPostDTO> updatePost(Integer postId, BookPostDTO postDTO) {
        validatePostDTO(postDTO);
        BookPost post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post = postRepository.save(post);
        BookPostDTO result = toBookPostDTO(post);
        return ApiResponse.<BookPostDTO>builder()
                .code(ErrorCode.USER_UPDATED_SUCCESSFULLY.getCode()) 
                .message("Post updated successfully")
                .result(result)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<Void> deletePost(Integer postId) {
        BookPost post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        postRepository.delete(post);
        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) 
                .message("Post deleted successfully")
                .result(null)
                .build();
    }

    @Override
    public ApiResponse<List<BookPostDTO>> findPostByTitle(String title) {
        List<BookPostDTO> posts = postRepository.findByTitleContaining(title).stream()
                .map(this::toBookPostDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<BookPostDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(posts)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<Void> likePost(PostLikeDTO postLikeDTO) {
        if (!userRepository.existsById(postLikeDTO.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!postRepository.existsById(postLikeDTO.getPostId())) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        Optional<Boolean> currentStatus = postRepository.findLikeStatus(postLikeDTO.getPostId(), postLikeDTO.getUserId());
        if (currentStatus.isPresent()) {
            postRepository.toggleLike(postLikeDTO.getPostId(), postLikeDTO.getUserId());
        } else {
            postRepository.savePostLike(postLikeDTO.getUserId(), postLikeDTO.getPostId(), postLikeDTO.getIsLike());
        }
        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) 
                .message("Post like updated successfully")
                .result(null)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<PostCommentDTO> commentOnPost(PostCommentDTO postCommentDTO) {
        if (!userRepository.existsById(postCommentDTO.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!postRepository.existsById(postCommentDTO.getPostId())) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        postRepository.savePostComment(
                postCommentDTO.getContent(),
                postCommentDTO.getUserId(),
                postCommentDTO.getPostId(),
                postCommentDTO.getParentId()
        );
        PostCommentDTO result = new PostCommentDTO(
                postCommentDTO.getContent(),
                postCommentDTO.getUserId(),
                postCommentDTO.getPostId(),
                postCommentDTO.getParentId()
        );
        return ApiResponse.<PostCommentDTO>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) 
                .message("Comment created successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<PostCommentDTO>> getCommentsByPostId(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        List<PostCommentDTO> comments = postRepository.findCommentsByPostId(postId).stream()
                .map(this::toPostCommentDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<PostCommentDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(comments)
                .build();
    }

    private BookPostDTO toBookPostDTO(BookPost post) {
        BookPostDTO dto = new BookPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setBookId(post.getBookId());
        dto.setUserId(post.getUserId());
        return dto;
    }

    private PostCommentDTO toPostCommentDTO(PostComment comment) {
        PostCommentDTO dto = new PostCommentDTO();
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUserId());
        dto.setPostId(comment.getPostId());
        dto.setParentId(comment.getParentId());
        return dto;
    }

    private void validatePostDTO(BookPostDTO postDTO) {
        if (!userRepository.existsById(postDTO.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!bookRepository.existsById(postDTO.getBookId())) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Book not found");
        }
    }
}