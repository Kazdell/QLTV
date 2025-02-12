package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import com.practice.QLTV.entity.RoleGroupFunction;
import com.practice.QLTV.repository.RoleGroupFunctionRepository;
import com.practice.QLTV.service.RoleGroupFunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleGroupFunctionServiceImpl implements RoleGroupFunctionService {

    private final RoleGroupFunctionRepository roleGroupFunctionRepository;

    @Override
    public RoleGroupFunctionDTO assignFunctionToRole(RoleGroupFunctionDTO roleFunctionDTO) {
        RoleGroupFunction roleFunction = RoleGroupFunction.builder()
                .roleGroupId(roleFunctionDTO.getRoleGroupId())
                .functionId(roleFunctionDTO.getFunctionId())
                .build();
        roleFunction = roleGroupFunctionRepository.save(roleFunction);
        return new RoleGroupFunctionDTO(roleFunction.getId(), roleFunction.getRoleGroupId(), roleFunction.getFunctionId());
    }

    @Override
    public List<RoleGroupFunctionDTO> getFunctionsByRoleId(Integer roleId) {
        return roleGroupFunctionRepository.findByRoleGroupId(roleId).stream()
                .map(roleFunction -> new RoleGroupFunctionDTO(roleFunction.getId(), roleFunction.getRoleGroupId(), roleFunction.getFunctionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FunctionDTO> getFunctionsByRoleGroup(Integer roleGroupId) {
        return roleGroupFunctionRepository.findFunctionsByRoleGroup(roleGroupId)
                .stream()
                .map(f -> new FunctionDTO(f.getId(), f.getFunctionCode(), f.getFunctionName(), f.getDescription()))
                .collect(Collectors.toList());
    }
}
