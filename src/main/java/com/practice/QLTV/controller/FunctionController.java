package com.practice.QLTV.controller;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.FunctionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
public class FunctionController {

    private final FunctionService functionService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<FunctionDTO>> createFunction(HttpServletRequest request, @Valid @RequestBody FunctionDTO functionDTO) {
        return ResponseEntity.ok(functionService.createFunction(functionDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FunctionDTO>>> getAllFunctions(HttpServletRequest request) {
        return ResponseEntity.ok(functionService.getAllFunctions());
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{functionCode}")
    public ResponseEntity<ApiResponse<FunctionDTO>> getFunctionByCode(HttpServletRequest request, @PathVariable String functionCode) {
        return ResponseEntity.ok(functionService.getFunctionByCode(functionCode));
    }
}