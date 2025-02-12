package com.practice.QLTV.controller;

import com.practice.QLTV.dto.RoleGroupDTO;
import com.practice.QLTV.service.RoleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleGroupController {

    private final RoleGroupService roleService;

    @PostMapping
    public ResponseEntity<RoleGroupDTO> createRole(@RequestBody RoleGroupDTO roleDTO) {
        return ResponseEntity.ok(roleService.createRole(roleDTO));
    }

    @GetMapping
    public ResponseEntity<List<RoleGroupDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleGroupDTO> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
