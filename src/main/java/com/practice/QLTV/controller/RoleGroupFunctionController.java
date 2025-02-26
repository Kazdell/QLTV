package com.practice.QLTV.controller;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.RoleGroupFunctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-functions")
@RequiredArgsConstructor
public class RoleGroupFunctionController {

    private final RoleGroupFunctionService roleGroupFunctionService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleGroupFunctionDTO>> assignFunctionToRole(@Valid @RequestBody RoleGroupFunctionDTO roleGroupFunctionDTO) {
        return ResponseEntity.ok(roleGroupFunctionService.assignFunctionToRole(roleGroupFunctionDTO));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<List<RoleGroupFunctionDTO>>> getFunctionsByRoleId(@PathVariable Integer roleId) {
        return ResponseEntity.ok(roleGroupFunctionService.getFunctionsByRoleId(roleId));
    }

    @GetMapping("/{roleGroupId}/functions")
    public ResponseEntity<ApiResponse<List<FunctionDTO>>> getFunctionsByRoleGroup(@PathVariable Integer roleGroupId) {
        return ResponseEntity.ok(roleGroupFunctionService.getFunctionsByRoleGroup(roleGroupId));
    }
}