package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.RoleGroupDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.RoleGroup;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.RoleGroupRepository;
import com.practice.QLTV.service.RoleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleGroupServiceImp implements RoleGroupService {

    private final RoleGroupRepository roleRepository;

    @Override
    public ApiResponse<RoleGroupDTO> createRole(RoleGroupDTO roleGroupDTO) {
        if (roleRepository.findByRoleGroupCode(roleGroupDTO.getRoleGroupCode()).isPresent()) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Role group code " + roleGroupDTO.getRoleGroupCode() + " already exists");
        }
        RoleGroup role = RoleGroup.builder()
                .roleGroupCode(roleGroupDTO.getRoleGroupCode())
                .roleGroupName(roleGroupDTO.getRoleGroupName())
                .description(roleGroupDTO.getDescription())
                .build();
        role = roleRepository.save(role);
        RoleGroupDTO result = toRoleGroupDTO(role);
        return ApiResponse.<RoleGroupDTO>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) 
                .message("Role group created successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<RoleGroupDTO>> getAllRoles() {
        List<RoleGroupDTO> roles = roleRepository.findAll().stream()
                .map(this::toRoleGroupDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<RoleGroupDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(roles)
                .build();
    }

    @Override
    public ApiResponse<RoleGroupDTO> getRoleById(Integer id) {
        RoleGroup role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        RoleGroupDTO result = toRoleGroupDTO(role);
        return ApiResponse.<RoleGroupDTO>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) 
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<Void> deleteRole(Integer id) {
        RoleGroup role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        roleRepository.delete(role);
        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) 
                .message("Role group deleted successfully")
                .result(null)
                .build();
    }

    private RoleGroupDTO toRoleGroupDTO(RoleGroup role) {
        return new RoleGroupDTO(
                role.getId(),
                role.getRoleGroupCode(),
                role.getRoleGroupName(),
                role.getDescription()
        );
    }
}