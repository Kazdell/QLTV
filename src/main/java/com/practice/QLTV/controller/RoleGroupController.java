package com.practice.QLTV.controller;

import com.practice.QLTV.dto.request.RoleGroupRequest;
import com.practice.QLTV.dto.response.RoleGroupResponse;
import com.practice.QLTV.entity.RoleGroup;
import com.practice.QLTV.service.RoleGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleGroupController {

    private final RoleGroupService roleGroupService;

    @PostMapping
    public ResponseEntity<RoleGroupResponse> createRoleGroup(@RequestBody @Validated RoleGroupRequest request) {
        RoleGroupResponse response = roleGroupService.createRoleGroup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleGroup>> getAllRoleGroups() {
        List<RoleGroup> roleGroups = roleGroupService.getAllRoleGroups();
        return ResponseEntity.ok(roleGroups);
    }

    @GetMapping("/{roleGroupCode}")
    public ResponseEntity<RoleGroupResponse> getRoleGroupByCode(@PathVariable String roleGroupCode) {
        RoleGroupResponse response = roleGroupService.getRoleGroupByCode(roleGroupCode);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{roleGroupCode}")
    public ResponseEntity<RoleGroupResponse> updateRoleGroup(
            @PathVariable String roleGroupCode,
            @RequestBody @Validated RoleGroupRequest request) {
        RoleGroupResponse response = roleGroupService.updateRoleGroup(roleGroupCode, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roleGroupCode}")
    public ResponseEntity<String> deleteRoleGroup(@PathVariable String roleGroupCode) {
        String message = roleGroupService.deleteRoleGroup(roleGroupCode);
        return ResponseEntity.ok(message);
    }
}
