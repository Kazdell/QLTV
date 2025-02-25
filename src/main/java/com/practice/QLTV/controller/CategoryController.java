package com.practice.QLTV.controller;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> deleteCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> addMultipleCategories(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(categoryService.addMultipleCategories(file));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> findCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> findCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }
}