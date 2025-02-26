package com.practice.QLTV.service;

import com.practice.QLTV.dto.RoleGroupDTO;
import com.practice.QLTV.dto.response.ApiResponse;

import java.util.List;

public interface RoleGroupService {
    ApiResponse<RoleGroupDTO> createRole(RoleGroupDTO roleGroupDTO);
    ApiResponse<List<RoleGroupDTO>> getAllRoles();
    ApiResponse<RoleGroupDTO> getRoleById(Integer id);
    ApiResponse<Void> deleteRole(Integer id);
}