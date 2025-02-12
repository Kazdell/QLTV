package com.practice.QLTV.repository;

import com.practice.QLTV.entity.BookPost;
import com.practice.QLTV.entity.PostComment;
import com.practice.QLTV.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<BookPost, Integer> {

    List<BookPost> findByTitleContaining(String title);

    @Modifying
    @Query("INSERT INTO PostLike (userId, postId, isLike) VALUES (?1, ?2, ?3)")
    void savePostLike(Integer userId, Integer postId, Boolean isLike);

    @Modifying
    @Query("INSERT INTO PostComment (content, userId, postId, parentId) VALUES (?1, ?2, ?3, ?4)")
    void savePostComment(String content, Integer userId, Integer postId, Integer parentId);

    @Query("SELECT c FROM PostComment c WHERE c.postId = ?1")
    List<PostComment> findCommentsByPostId(Integer postId);

    @Modifying
    @Query("UPDATE PostLike l SET l.isLike = NOT l.isLike WHERE l.postId = ?1 AND l.userId = ?2")
    void toggleLike(Integer postId, Integer userId);

    @Query("SELECT l.isLike FROM PostLike l WHERE l.postId = ?1 AND l.userId = ?2")
    Optional<Boolean> findLikeStatus(Integer postId, Integer userId);


    @Query("SELECT l FROM PostLike l WHERE l.postId = ?1 AND l.userId = ?2")
    List<PostLike> findLikesByPostIdAndUserId(Integer postId, Integer userId);
}
