package com.practice.QLTV.service;

import com.practice.QLTV.dto.RoleGroupDTO;
import java.util.List;

public interface RoleGroupService {
    RoleGroupDTO createRole(RoleGroupDTO roleGroupDTO);
    List<RoleGroupDTO> getAllRoles();
    RoleGroupDTO getRoleById(Integer id);
    void deleteRole(Integer id);
}
