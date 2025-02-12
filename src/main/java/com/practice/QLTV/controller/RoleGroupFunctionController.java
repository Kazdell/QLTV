package com.practice.QLTV.controller;

import com.practice.QLTV.dto.request.RoleGroupFunctionRequest;
import com.practice.QLTV.entity.RoleGroupFunction;
import com.practice.QLTV.service.RoleGroupFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/role-group-functions")
public class RoleGroupFunctionController {

    private final RoleGroupFunctionService roleGroupFunctionService;

    @PostMapping
    public ResponseEntity<RoleGroupFunction> createRoleGroupFunction(@RequestBody @Validated RoleGroupFunctionRequest request) {
        RoleGroupFunction response = roleGroupFunctionService.createRoleGroupFunction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roleGroupId}")
    public ResponseEntity<List<RoleGroupFunction>> getFunctionsByRoleGroup(@PathVariable Integer roleGroupId) {
        List<RoleGroupFunction> response = roleGroupFunctionService.getFunctionsByRoleGroup(roleGroupId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleGroupFunction> updateRoleGroupFunction(
            @PathVariable Integer id,
            @RequestBody @Validated RoleGroupFunctionRequest request) {
        RoleGroupFunction response = roleGroupFunctionService.updateRoleGroupFunction(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleGroupFunction(@PathVariable Integer id) {
        String message = roleGroupFunctionService.deleteRoleGroupFunction(id);
        return ResponseEntity.ok(message);
    }
}
