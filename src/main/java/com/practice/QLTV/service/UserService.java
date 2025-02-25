package com.practice.QLTV.service;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    ApiResponse<UserResponse> getUserById(Integer id);
    ApiResponse<PageResponse<UserResponse>> getAllUsersByStatus(Pageable pageable, UserStatus status);
    ApiResponse<UserResponse> updateUserStatus(Integer id, UserStatus newStatus); // Changed from String
    ApiResponse<UserResponse> createUser(UserCreationRequest request);
    ApiResponse<UserResponse> updateUser(Integer id, UserUpdateRequest request);
    ApiResponse<PageResponse<UserResponse>> searchUsers(Pageable pageable, UserSearchingRequest criteria);
    ApiResponse<PageResponse<UserResponse>> searchAdvancedUsers(Pageable pageable, String keyword);
    ApiResponse<UserResponse> getCurrentUserInfo(); // Changed from User

    enum UserStatus {
        ACTIVE(true, false),
        BANNED(false, false),
        DELETED(false, true);

        private final boolean isActive;
        private final boolean isDeleted;

        UserStatus(boolean isActive, boolean isDeleted) {
            this.isActive = isActive;
            this.isDeleted = isDeleted;
        }

        public boolean getIsActive() { return isActive; }
        public boolean getIsDeleted() { return isDeleted; }
    }
}