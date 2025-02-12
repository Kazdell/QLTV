package com.practice.QLTV.service;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;

public interface UserService {
    UserResponse getUserById(int id);
    PageResponse<UserResponse> getAllUserActive(int page, int size, String sortBy, String sortDirection);
    PageResponse<UserResponse>  getAllUserDeleted(int page, int size, String sortBy, String sortDirection);
    PageResponse<UserResponse>  getAllUserBanned(int page, int size, String sortBy, String sortDirection);
    ApiResponse<String> activeUserById(int id);
    ApiResponse<String> deleteUserById(int id);
    ApiResponse<String> banUserById(int id);
    UserResponse createUser(UserCreationRequest creationRequest);
    UserResponse updateUser(UserUpdateRequest updateRequest, int id);
    PageResponse<UserResponse> searchUser(int page, int size, String sortBy, String sortDirection, UserSearchingRequest searchingRequest);
    PageResponse<UserResponse> searchAdvancedUser(int page, int size, String sortBy, String sortDirection, String keyword);
    User MyInfo();
}
