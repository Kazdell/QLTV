package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserSearchingRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.dto.response.PageResponse;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.mapper.UserMapper;
import com.practice.QLTV.repository.UserRepository;
import com.practice.QLTV.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponse getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return userMapper.toUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUserActive(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<User> pageData = userRepository.getAllUserActive(pageable);
        return mapToPageResponse(page, pageData);
    }

    @Override
    public PageResponse<UserResponse> getAllUserDeleted(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<User> pageData = userRepository.getAllUserDeleted(pageable);
        return mapToPageResponse(page, pageData);
    }

    @Override
    public PageResponse<UserResponse> getAllUserBanned(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<User> pageData = userRepository.getAllUserBanned(pageable);
        return mapToPageResponse(page, pageData);
    }

    @Override
    @Transactional
    public ApiResponse<String> activeUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setIsActive(true);
        userRepository.save(user);
        return ApiResponse.<String>builder()
                .code(200)
                .message("User activated successfully.")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setIsDeleted(true);
        userRepository.save(user);
        return ApiResponse.<String>builder()
                .code(200)
                .message("User deleted successfully.")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> banUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setIsActive(false);
        userRepository.save(user);
        return ApiResponse.<String>builder()
                .code(200)
                .message("User banned successfully.")
                .build();
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest creationRequest) {
        if (userRepository.existsByUsername(creationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toEntity(creationRequest);
        user.setRoleGroupId(1);
        user.setIsActive(true);
        user.setIsDeleted(false);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateRequest updateRequest, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateEntity(user, updateRequest);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public PageResponse<UserResponse> searchUser(int page, int size, String sortBy, String sortDirection, UserSearchingRequest searchingRequest) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<User> pageData = userRepository.searchUsers(
                searchingRequest.getUsername(),
                searchingRequest.getPhoneNumber(),
                searchingRequest.getEmail(),
                pageable
        );
        return mapToPageResponse(page, pageData);
    }

    @Override
    public PageResponse<UserResponse> searchAdvancedUser(int page, int size, String sortBy, String sortDirection, String keyword) {
        Pageable pageable = pagingDirection(page, size, sortBy, sortDirection);
        var pageData = userRepository.searchAdvancedUser(pageable, keyword);
        List<UserResponse> userResponseList = pageData.stream().map(userMapper::toUserResponse).toList();
        return PageResponse.<UserResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(userResponseList)
                .build();
    }

    @Override
    public User MyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return user;
    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.ASC);
        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }

    private Pageable pagingDirection(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortDirection);
        } catch (IllegalArgumentException e) {
            direction = Sort.Direction.ASC;
        }
        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }

    private PageResponse<UserResponse> mapToPageResponse(int currentPage, Page<User> pageData) {
        List<UserResponse> responses = pageData.getContent().stream()
                .map(userMapper::toUserResponse)
                .toList();
        return PageResponse.<UserResponse>builder()
                .currentPage(currentPage)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(responses)
                .build();
    }
}
