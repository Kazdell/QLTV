package com.practice.QLTV.service;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import java.util.List;

public interface RoleGroupFunctionService {
    RoleGroupFunctionDTO assignFunctionToRole(RoleGroupFunctionDTO roleFunctionDTO);
    List<RoleGroupFunctionDTO> getFunctionsByRoleId(Integer roleId);
    List<FunctionDTO> getFunctionsByRoleGroup(Integer roleGroupId);
}
