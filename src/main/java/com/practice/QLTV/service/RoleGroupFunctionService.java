package com.practice.QLTV.service;

import com.practice.QLTV.dto.request.RoleGroupFunctionRequest;
import com.practice.QLTV.entity.RoleGroupFunction;

import java.util.List;

public interface RoleGroupFunctionService {
    RoleGroupFunction createRoleGroupFunction(RoleGroupFunctionRequest request);

    List<RoleGroupFunction> getFunctionsByRoleGroup(Integer roleGroupId);

    RoleGroupFunction updateRoleGroupFunction(Integer id, RoleGroupFunctionRequest request);

    String deleteRoleGroupFunction(Integer id);
}
