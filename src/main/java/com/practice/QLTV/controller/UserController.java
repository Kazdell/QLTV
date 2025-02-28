package com.practice.QLTV.controller;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getUsers(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
            @RequestParam(defaultValue = "ACTIVE") UserService.UserStatus status) {
        return ResponseEntity.ok(userService.getAllUsersByStatus(pageable, status));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserCreationRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable Integer id,
            @RequestParam UserService.UserStatus status) {
        return ResponseEntity.ok(userService.updateUserStatus(id, status));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchUsers(
            @PageableDefault(page = 0, size = 10, sort = "username") Pageable pageable,
            @RequestBody UserSearchingRequest criteria) {
        return ResponseEntity.ok(userService.searchUsers(pageable, criteria));
    }

    @GetMapping("/search/advanced")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchAdvancedUsers(
            @PageableDefault(page = 0, size = 10, sort = "username") Pageable pageable,
            @RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchAdvancedUsers(pageable, keyword));
    }

    @GetMapping("/myinfo")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserInfo() {
        return ResponseEntity.ok(userService.getCurrentUserInfo());
    }
}