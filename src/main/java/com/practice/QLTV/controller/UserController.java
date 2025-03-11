package com.practice.QLTV.controller;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(HttpServletRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getUsers(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
            @RequestParam(defaultValue = "ACTIVE") UserService.UserStatus status) {
        return ResponseEntity.ok(userService.getAllUsersByStatus(pageable, status));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            HttpServletRequest request,
            @Valid @RequestBody UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }

    @PreAuthorize("fileRole(#request)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            HttpServletRequest request,
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
    }

    @PreAuthorize("fileRole(#request)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            HttpServletRequest request,
            @PathVariable Integer id,
            @RequestParam UserService.UserStatus status) {
        return ResponseEntity.ok(userService.updateUserStatus(id, status));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchUsers(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "username") Pageable pageable,
            @RequestBody UserSearchingRequest criteria) {
        return ResponseEntity.ok(userService.searchUsers(pageable, criteria));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/search/advanced")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchAdvancedUsers(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "username") Pageable pageable,
            @RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchAdvancedUsers(pageable, keyword));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/myinfo")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getCurrentUserInfo());
    }
}