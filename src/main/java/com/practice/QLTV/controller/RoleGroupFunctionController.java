package com.practice.QLTV.controller;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.RoleGroupFunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.RoleGroupFunctionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-functions")
@RequiredArgsConstructor
public class RoleGroupFunctionController {

    private final RoleGroupFunctionService roleGroupFunctionService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleGroupFunctionDTO>> assignFunctionToRole(HttpServletRequest request, @Valid @RequestBody RoleGroupFunctionDTO roleGroupFunctionDTO) {
        return ResponseEntity.ok(roleGroupFunctionService.assignFunctionToRole(roleGroupFunctionDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<List<RoleGroupFunctionDTO>>> getFunctionsByRoleId(HttpServletRequest request, @PathVariable Integer roleId) {
        return ResponseEntity.ok(roleGroupFunctionService.getFunctionsByRoleId(roleId));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{roleGroupId}/functions")
    public ResponseEntity<ApiResponse<List<FunctionDTO>>> getFunctionsByRoleGroup(HttpServletRequest request, @PathVariable Integer roleGroupId) {
        return ResponseEntity.ok(roleGroupFunctionService.getFunctionsByRoleGroup(roleGroupId));
    }
}