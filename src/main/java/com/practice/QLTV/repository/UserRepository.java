package com.practice.QLTV.repository;

import com.practice.QLTV.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsById(Integer id);

    @Query("SELECT u FROM User u WHERE u.isActive = true")
    Page<User> getAllUserActive(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isDeleted = true")
    Page<User> getAllUserDeleted(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isActive = false")
    Page<User> getAllUserBanned(Pageable pageable);

    @Query("UPDATE User u SET u.isActive = true WHERE u.id = :id")
    int activeUserById(int id);

    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    int deleteUserById(int id);

    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :id")
    int banUserById(int id);

    @Query("""
        SELECT u FROM User u 
        WHERE (:username IS NULL OR u.username LIKE %:username%) 
          AND (:phoneNumber IS NULL OR u.phoneNumber LIKE %:phoneNumber%)
          AND (:email IS NULL OR u.email LIKE %:email%)
    """)
    Page<User> searchUsers(@Param("username") String username,
                           @Param("phoneNumber") String phoneNumber,
                           @Param("email") String email,
                           Pageable pageable);

    @Query("""
        SELECT u FROM User u 
        WHERE u.username LIKE %:keyword% 
           OR u.email LIKE %:keyword% 
           OR u.phoneNumber LIKE %:keyword%
    """)
    Page<User> searchAdvancedUser(Pageable pageable, String keyword);
    @Query("""
        SELECT u FROM User u 
        WHERE u.username LIKE %:keyword% 
           OR u.email LIKE %:keyword% 
           OR u.phoneNumber LIKE %:keyword%
    """)
    List<User> searchAdvancedUser(String keyword);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT i.phoneNumber FROM User i")
    List<String> findAllPhoneNumber();

    @Query("SELECT i.username FROM User i")
    List<String> findAllUsername();

    @Query("SELECT i.email FROM User i")
    List<String> findAllEmail();

    @Query("SELECT i.identityNumber FROM User i")
    List<String> findAllIdentityNumbers();

    public boolean existsByPhoneNumber(String phoneNumber);
    public boolean existsByEmail(String email);
}
