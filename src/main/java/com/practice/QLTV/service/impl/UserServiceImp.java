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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<UserResponse> getUserById(Integer id) {
        log.debug("Fetching user with ID: {}", id);
        User user = findUserById(id);
        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public ApiResponse<PageResponse<UserResponse>> getAllUsersByStatus(Pageable pageable, UserStatus status) {
        log.debug("Fetching {} users with page: {}", status, pageable);
        Page<User> userPage = switch (status) {
            case ACTIVE -> userRepository.getAllUserActive(pageable);
            case DELETED -> userRepository.getAllUserDeleted(pageable);
            case BANNED -> userRepository.getAllUserBanned(pageable);
        };

        PageResponse<UserResponse> pageResponse = convertToPageResponse(userPage);
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(pageResponse)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> updateUserStatus(Integer id, UserStatus newStatus) {
        log.debug("Updating user {} status to: {}", id, newStatus);
        User user = findUserById(id);

        if (user.getIsActive() == newStatus.getIsActive() &&
                user.getIsDeleted() == newStatus.getIsDeleted()) {
            throw new AppException(ErrorCode.INVALID_REQUEST_FORMAT, "User is already in " + newStatus + " status");
        }

        user.setIsActive(newStatus.getIsActive());
        user.setIsDeleted(newStatus.getIsDeleted());
        User updatedUser = userRepository.save(user);

        log.info("User {} status updated to: {}", id, newStatus);
        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.UPDATE_STATUS_SUCCESSFUL.getCode()) // 1000
                .message(ErrorCode.UPDATE_STATUS_SUCCESSFUL.getMessage()) // "User status updated successfully"
                .result(userMapper.toUserResponse(updatedUser))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> createUser(UserCreationRequest request) {
        log.debug("Creating new user with username: {}", request.getUsername());
        validateNewUser(request);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoleGroupId(1); // Consider validating this against a RoleGroupRepository
        user.setIsActive(true);
        user.setIsDeleted(false);

        User savedUser = userRepository.save(user);
        log.info("Created new user with ID: {}", savedUser.getId());

        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_CREATED_SUCCESSFULLY.getMessage()) // "User created successfully"
                .result(userMapper.toUserResponse(savedUser))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> updateUser(Integer id, UserUpdateRequest request) {
        log.debug("Updating user with ID: {}", id);
        User user = findUserById(id);
        validateUserUpdate(user, request);

        userMapper.updateEntity(user, request);
        User savedUser = userRepository.save(user);

        log.info("Updated user with ID: {}", id);
        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.USER_UPDATED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_UPDATED_SUCCESSFULLY.getMessage()) // "User updated successfully"
                .result(userMapper.toUserResponse(savedUser))
                .build();
    }

    @Override
    public ApiResponse<PageResponse<UserResponse>> searchUsers(Pageable pageable, UserSearchingRequest criteria) {
        log.debug("Searching users with criteria: {}", criteria);
        Page<User> userPage = userRepository.searchUsers(
                criteria.getUsername(),
                criteria.getPhoneNumber(),
                criteria.getEmail(),
                pageable
        );

        PageResponse<UserResponse> pageResponse = convertToPageResponse(userPage);
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(pageResponse)
                .build();
    }

    @Override
    public ApiResponse<PageResponse<UserResponse>> searchAdvancedUsers(Pageable pageable, String keyword) {
        log.debug("Performing advanced search with keyword: {}", keyword);
        Page<User> userPage = userRepository.searchAdvancedUser(pageable, keyword);

        PageResponse<UserResponse> pageResponse = convertToPageResponse(userPage);
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(pageResponse)
                .build();
    }

    @Override
    public ApiResponse<UserResponse> getCurrentUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Fetching current user info for: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage()) // "Retrieve successfully"
                .result(userMapper.toUserResponse(user))
                .build();
    }

    // Helper methods
    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    private void validateNewUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (userRepository.existsByIdentityNumber(request.getIdentityNumber())) {
            throw new AppException(ErrorCode.IDENTITY_NUMBER_EXISTED);
        }
        if (userRepository.findByUsername(request.getUsername())
                .filter(user -> user.getIsDeleted()).isPresent()) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }
    }

    private void validateUserUpdate(User user, UserUpdateRequest request) {
        if (request.getEmail() != null && !user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (request.getPhoneNumber() != null && !user.getPhoneNumber().equals(request.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        if (request.getIdentityNumber() != null && !user.getIdentityNumber().equals(request.getIdentityNumber()) &&
                userRepository.existsByIdentityNumber(request.getIdentityNumber())) {
            throw new AppException(ErrorCode.IDENTITY_NUMBER_EXISTED);
        }
        if (user.getIsDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_LOCKED);
        }
    }

    private PageResponse<UserResponse> convertToPageResponse(Page<User> page) {
        List<UserResponse> userResponses = page.getContent().stream()
                .map(userMapper::toUserResponse)
                .toList();

        return new PageResponse<>(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                userResponses
        );
    }
}