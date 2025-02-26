package com.practice.QLTV.controller;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.FunctionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
public class FunctionController {

    private final FunctionService functionService;

    @PostMapping
    public ResponseEntity<ApiResponse<FunctionDTO>> createFunction(@Valid @RequestBody FunctionDTO functionDTO) {
        return ResponseEntity.ok(functionService.createFunction(functionDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FunctionDTO>>> getAllFunctions() {
        return ResponseEntity.ok(functionService.getAllFunctions());
    }

    @GetMapping("/{functionCode}")
    public ResponseEntity<ApiResponse<FunctionDTO>> getFunctionByCode(@PathVariable String functionCode) {
        return ResponseEntity.ok(functionService.getFunctionByCode(functionCode));
    }
}