package com.practice.QLTV.service;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;

import java.util.List;

public interface RoleGroupFunctionService {
    ApiResponse<RoleGroupFunctionDTO> assignFunctionToRole(RoleGroupFunctionDTO roleFunctionDTO);
    ApiResponse<List<RoleGroupFunctionDTO>> getFunctionsByRoleId(Integer roleId);
    ApiResponse<List<FunctionDTO>> getFunctionsByRoleGroup(Integer roleGroupId);
}