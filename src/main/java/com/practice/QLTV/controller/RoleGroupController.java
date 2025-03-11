package com.practice.QLTV.controller;

import com.practice.QLTV.dto.RoleGroupDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.RoleGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleGroupController {

    private final RoleGroupService roleService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleGroupDTO>> createRole(HttpServletRequest request, @Valid @RequestBody RoleGroupDTO roleDTO) {
        return ResponseEntity.ok(roleService.createRole(roleDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleGroupDTO>>> getAllRoles(HttpServletRequest request) {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleGroupDTO>> getRoleById(HttpServletRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PreAuthorize("fileRole(#request)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(HttpServletRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}