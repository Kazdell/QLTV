package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.RoleGroupDTO;
import com.practice.QLTV.entity.RoleGroup;
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
    public RoleGroupDTO createRole(RoleGroupDTO roleGroupDTO) {
        RoleGroup role = RoleGroup.builder()
                .roleGroupCode(roleGroupDTO.getRoleGroupCode())
                .roleGroupName(roleGroupDTO.getRoleGroupName())
                .description(roleGroupDTO.getDescription())
                .build();
        role = roleRepository.save(role);
        return new RoleGroupDTO(role.getId(), role.getRoleGroupCode(), role.getRoleGroupName(), role.getDescription());
    }

    @Override
    public List<RoleGroupDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleGroupDTO(role.getId(), role.getRoleGroupCode(), role.getRoleGroupName(), role.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleGroupDTO getRoleById(Integer id) {
        RoleGroup role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return new RoleGroupDTO(role.getId(), role.getRoleGroupCode(), role.getRoleGroupName(), role.getDescription());
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
