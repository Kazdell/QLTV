package com.practice.QLTV.mapper;

import com.practice.QLTV.dto.request.UserCreationRequest;
import com.practice.QLTV.dto.request.UserUpdateRequest;
import com.practice.QLTV.dto.response.UserResponse;
import com.practice.QLTV.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toEntity(UserCreationRequest userCreationRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User updateEntity(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    @BeforeMapping
    default void beforeUpdate(UserUpdateRequest userUpdateRequest, @MappingTarget User user) {
        if (userUpdateRequest.getUsername() == null) {
            userUpdateRequest.setUsername(user.getUsername()); 
        }
        if (userUpdateRequest.getPassword() == null) {
            userUpdateRequest.setPassword(user.getPassword()); 
        }
        if (userUpdateRequest.getFullName() == null) {
            userUpdateRequest.setFullName(user.getFullName()); 
        }
        if (userUpdateRequest.getPhoneNumber() == null) {
            userUpdateRequest.setPhoneNumber(user.getPhoneNumber()); 
        }
        if (userUpdateRequest.getIdentityNumber() == null) {
            userUpdateRequest.setIdentityNumber(user.getIdentityNumber()); 
        }
        if (userUpdateRequest.getDob() == null) {
            userUpdateRequest.setDob(user.getDob()); 
        }
        if (userUpdateRequest.getEmail() == null) {
            userUpdateRequest.setEmail(user.getEmail()); 
        }
        if (userUpdateRequest.getAddress() == null) {
            userUpdateRequest.setAddress(user.getAddress()); 
        }
        if (userUpdateRequest.getRoleGroupId() == null) {
            userUpdateRequest.setRoleGroupId(user.getRoleGroupId()); 
        }
    }
}
