package com.practice.QLTV.service;

import com.practice.QLTV.dto.request.RoleGroupRequest;
import com.practice.QLTV.dto.response.RoleGroupResponse;
import com.practice.QLTV.entity.RoleGroup;

import java.util.List;

public interface RoleGroupService {
    RoleGroupResponse createRoleGroup(RoleGroupRequest roleGroupRequest);

    List<RoleGroup> getAllRoleGroups();

    RoleGroupResponse getRoleGroupByCode(String roleGroupCode);

    RoleGroupResponse updateRoleGroup(String roleGroupCode, RoleGroupRequest roleGroupRequest);

    String deleteRoleGroup(String roleGroupCode);
}
