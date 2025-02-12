package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.request.RoleGroupRequest;
import com.practice.QLTV.dto.response.RoleGroupResponse;
import com.practice.QLTV.entity.RoleGroup;
import com.practice.QLTV.repository.RoleGroupRepository;
import com.practice.QLTV.service.RoleGroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleGroupServiceImp implements RoleGroupService {

    RoleGroupRepository roleGroupRepository;

    @Override
    public RoleGroupResponse createRoleGroup(RoleGroupRequest roleGroupRequest) {
        RoleGroup roleGroup = RoleGroup.builder()
                .roleGroupCode(roleGroupRequest.getRoleGroupCode())
                .roleGroupName(roleGroupRequest.getRoleGroupName())
                .description(roleGroupRequest.getDescription())
                .build();

        RoleGroup savedRoleGroup = roleGroupRepository.save(roleGroup);
        return RoleGroupResponse.builder()
                .roleGroupCode(savedRoleGroup.getRoleGroupCode())
                .roleGroupName(savedRoleGroup.getRoleGroupName())
                .description(savedRoleGroup.getDescription())
                .build();
    }

    @Override
    public List<RoleGroup> getAllRoleGroups() {
        return (roleGroupRepository.findAll());
    }

    @Override
    public RoleGroupResponse getRoleGroupByCode(String roleGroupCode) {
        RoleGroup roleGroup = roleGroupRepository.findByRoleGroupCode(roleGroupCode)
                .orElseThrow(() -> new RuntimeException("Role group code does not exist"));
        return RoleGroupResponse.builder()
                .roleGroupCode(roleGroup.getRoleGroupCode())
                .roleGroupName(roleGroup.getRoleGroupName())
                .description(roleGroup.getDescription())
                .build();
    }

    @Override
    public RoleGroupResponse updateRoleGroup(String roleGroupCode, RoleGroupRequest roleGroupRequest) {
        RoleGroup existingRoleGroup = roleGroupRepository.findByRoleGroupCode(roleGroupCode)
                .orElseThrow(() -> new RuntimeException("Role group code does not exist"));

        existingRoleGroup.setRoleGroupCode(roleGroupRequest.getRoleGroupCode());
        existingRoleGroup.setRoleGroupName(roleGroupRequest.getRoleGroupName());
        existingRoleGroup.setDescription(roleGroupRequest.getDescription());

        RoleGroup updatedRoleGroup = roleGroupRepository.save(existingRoleGroup);
        return RoleGroupResponse.builder()
                .roleGroupCode(updatedRoleGroup.getRoleGroupCode())
                .roleGroupName(updatedRoleGroup.getRoleGroupName())
                .description(updatedRoleGroup.getDescription())
                .build();
    }

    @Override
    public String deleteRoleGroup(String roleGroupCode) {
        RoleGroup existingRoleGroup = roleGroupRepository.findByRoleGroupCode(roleGroupCode)
                .orElseThrow(() -> new RuntimeException("Role group code does not exist"));

        roleGroupRepository.delete(existingRoleGroup);
        return "Role group has been deleted";
    }
}
