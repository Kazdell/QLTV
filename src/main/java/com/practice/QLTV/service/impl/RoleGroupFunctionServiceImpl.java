package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.Function;
import com.practice.QLTV.entity.RoleGroupFunction;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.FunctionRepository;
import com.practice.QLTV.repository.RoleGroupFunctionRepository;
import com.practice.QLTV.repository.RoleGroupRepository;
import com.practice.QLTV.service.RoleGroupFunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleGroupFunctionServiceImpl implements RoleGroupFunctionService {

    private final RoleGroupFunctionRepository roleGroupFunctionRepository;
    private final RoleGroupRepository roleGroupRepository;
    private final FunctionRepository functionRepository;

    private static final int ADMIN_ROLE_ID = 2; // Hardcoded for ADMIN role

    @Override
    public ApiResponse<RoleGroupFunctionDTO> assignFunctionToRole(RoleGroupFunctionDTO roleFunctionDTO) {
        if (!roleGroupRepository.existsById(roleFunctionDTO.getRoleGroupId())) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Role group not found");
        }
        if (!functionRepository.existsById(roleFunctionDTO.getFunctionId())) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Function not found");
        }
        RoleGroupFunction roleFunction = RoleGroupFunction.builder()
                .roleGroupId(roleFunctionDTO.getRoleGroupId())
                .functionId(roleFunctionDTO.getFunctionId())
                .build();
        roleFunction = roleGroupFunctionRepository.save(roleFunction);
        RoleGroupFunctionDTO result = toRoleGroupFunctionDTO(roleFunction);
        return ApiResponse.<RoleGroupFunctionDTO>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode())
                .message("Function assigned to role successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<RoleGroupFunctionDTO>> getFunctionsByRoleId(Integer roleId) {
        if (!roleGroupRepository.existsById(roleId)) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Role group not found");
        }
        List<RoleGroupFunctionDTO> functions = roleGroupFunctionRepository.findByRoleGroupId(roleId).stream()
                .map(this::toRoleGroupFunctionDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<RoleGroupFunctionDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode())
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(functions)
                .build();
    }

    @Override
    public ApiResponse<List<FunctionDTO>> getFunctionsByRoleGroup(Integer roleGroupId) {
        if (!roleGroupRepository.existsById(roleGroupId)) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Role group not found");
        }
        List<FunctionDTO> functions = roleGroupFunctionRepository.findFunctionsByRoleGroup(roleGroupId).stream()
                .map(this::toFunctionDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<FunctionDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode())
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(functions)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<Void> assignAllFunctionsToAdmin() {
        if (!roleGroupRepository.existsById(ADMIN_ROLE_ID)) {
            throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Admin role with ID " + ADMIN_ROLE_ID + " not found");
        }
        List<Function> allFunctions = functionRepository.findAll();

        // Assign each function to ADMIN role if not already assigned
        List<RoleGroupFunction> existingMappings = roleGroupFunctionRepository.findByRoleGroupId(ADMIN_ROLE_ID);
        List<Integer> existingFunctionIds = existingMappings.stream()
                .map(RoleGroupFunction::getFunctionId)
                .collect(Collectors.toList());

        List<RoleGroupFunction> newMappings = allFunctions.stream()
                .filter(function -> !existingFunctionIds.contains(function.getId()))
                .map(function -> RoleGroupFunction.builder()
                        .roleGroupId(ADMIN_ROLE_ID)
                        .functionId(function.getId())
                        .build())
                .collect(Collectors.toList());

        if (!newMappings.isEmpty()) {
            roleGroupFunctionRepository.saveAll(newMappings);
        }

        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode())
                .message("All functions assigned to ADMIN role successfully")
                .result(null)
                .build();
    }

    private RoleGroupFunctionDTO toRoleGroupFunctionDTO(RoleGroupFunction roleFunction) {
        return new RoleGroupFunctionDTO(
                roleFunction.getId(),
                roleFunction.getRoleGroupId(),
                roleFunction.getFunctionId()
        );
    }

    private FunctionDTO toFunctionDTO(Function function) {
        return new FunctionDTO(
                function.getId(),
                function.getFunctionCode(),
                function.getFunctionName(),
                function.getDescription()
        );
    }
}