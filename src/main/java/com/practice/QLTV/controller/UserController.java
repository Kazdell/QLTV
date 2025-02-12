package com.practice.QLTV.controller;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.service.UserService;
import com.practice.QLTV.utils.UtilsExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;
    UtilsExcel exportUsersExcel;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<PageResponse<UserResponse>> getAllUserActive(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        PageResponse<UserResponse> response = userService.getAllUserActive(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deleted")
    public ResponseEntity<PageResponse<UserResponse>> getAllUserDeleted(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        PageResponse<UserResponse> response = userService.getAllUserDeleted(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/banned")
    public ResponseEntity<PageResponse<UserResponse>> getAllUserBanned(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        PageResponse<UserResponse> response = userService.getAllUserBanned(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable int id,
            @RequestBody UserUpdateRequest request
    ) {
        UserResponse userResponse = userService.updateUser(request, id);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable int id) {
        ApiResponse<String> response = userService.deleteUserById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<ApiResponse<String>> activateUserById(@PathVariable int id) {
        ApiResponse<String> response = userService.activeUserById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/ban/{id}")
    public ResponseEntity<ApiResponse<String>> banUserById(@PathVariable int id) {
        ApiResponse<String> response = userService.banUserById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<UserResponse>> searchUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "username") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            UserSearchingRequest request
    ) {
        PageResponse<UserResponse> response = userService.searchUser(page, size, sortBy, sortDirection, request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/search-advanced")
    public ResponseEntity<PageResponse<UserResponse>> searchAdvancedUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "username") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam String keyword
    ) {
        PageResponse<UserResponse> response = userService.searchAdvancedUser(page, size, sortBy, sortDirection, keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/export-excel-search")
    public void exportSearchUsersToExcel(HttpServletResponse response, @RequestParam(required = false) String keyword) throws IOException {
        boolean isSearch = (keyword != null && !keyword.trim().isEmpty());
        exportUsersExcel.generateExcel(response, isSearch, keyword);
    }

    @PostMapping("/import-excel")
    public ResponseEntity<ApiResponse<String>> uploadExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        ApiResponse<String> apiResponse = exportUsersExcel.getTemporaryFileInServer(file, response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/myinfo")
    User getmyinfo(){
        return userService.MyInfo();
    }

}

