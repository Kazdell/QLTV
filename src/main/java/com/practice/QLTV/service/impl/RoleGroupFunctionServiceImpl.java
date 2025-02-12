package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.request.RoleGroupFunctionRequest;
import com.practice.QLTV.entity.RoleGroupFunction;
import com.practice.QLTV.repository.RoleGroupFunctionRepository;
import com.practice.QLTV.service.RoleGroupFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleGroupFunctionServiceImpl implements RoleGroupFunctionService {

    private final RoleGroupFunctionRepository roleGroupFunctionRepository;

    @Override
    public RoleGroupFunction createRoleGroupFunction(RoleGroupFunctionRequest request) {
        RoleGroupFunction roleGroupFunction = RoleGroupFunction.builder()
                .roleGroupId(request.getRoleGroupId())
                .functionId(request.getFunctionId())
                .build();

        return roleGroupFunctionRepository.save(roleGroupFunction);
    }

    @Override
    public List<RoleGroupFunction> getFunctionsByRoleGroup(Integer roleGroupId) {
        return roleGroupFunctionRepository.findByRoleGroupId(roleGroupId);
    }

    @Override
    public RoleGroupFunction updateRoleGroupFunction(Integer id, RoleGroupFunctionRequest request) {
        RoleGroupFunction existingRoleGroupFunction = roleGroupFunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoleGroupFunction not found"));

        existingRoleGroupFunction.setRoleGroupId(request.getRoleGroupId());
        existingRoleGroupFunction.setFunctionId(request.getFunctionId());

        return roleGroupFunctionRepository.save(existingRoleGroupFunction);
    }

    @Override
    public String deleteRoleGroupFunction(Integer id) {
        RoleGroupFunction existingRoleGroupFunction = roleGroupFunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoleGroupFunction not found"));

        roleGroupFunctionRepository.delete(existingRoleGroupFunction);
        return "RoleGroupFunction deleted successfully";
    }
}