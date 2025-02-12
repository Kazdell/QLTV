package com.practice.QLTV.service;

import com.practice.QLTV.dto.BookPostDTO;
import com.practice.QLTV.dto.PostCommentDTO;
import com.practice.QLTV.dto.PostLikeDTO;
import com.practice.QLTV.entity.BookPost;
import com.practice.QLTV.entity.PostComment;
import java.util.List;

public interface PostService {
    BookPost createPost(BookPostDTO postDTO);

    BookPost updatePost(Integer postId, BookPostDTO postDTO);

    void deletePost(Integer postId);

    List<BookPost> findPostByTitle(String title);

    void likePost(PostLikeDTO postLikeDTO);

    PostComment commentOnPost(PostCommentDTO postCommentDTO);

    List<PostComment> getCommentsByPostId(Integer postId);
}
