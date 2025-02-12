package com.practice.QLTV.controller;

import com.practice.QLTV.dto.FunctionDTO;
import com.practice.QLTV.service.FunctionService;
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
    public ResponseEntity<FunctionDTO> createFunction(@RequestBody FunctionDTO functionDTO) {
        return ResponseEntity.ok(functionService.createFunction(functionDTO));
    }

    @GetMapping
    public ResponseEntity<List<FunctionDTO>> getAllFunctions() {
        return ResponseEntity.ok(functionService.getAllFunctions());
    }
}
