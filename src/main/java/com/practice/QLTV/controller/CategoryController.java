package com.practice.QLTV.controller;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("fileRole(#request)")
    @PostMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> createCategory(HttpServletRequest request, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(HttpServletRequest request, @PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @PreAuthorize("fileRole(#request)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> deleteCategory(HttpServletRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @PreAuthorize("fileRole(#request)")
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> addMultipleCategories(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(categoryService.addMultipleCategories(file));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> findAllCategories(HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> findCategoryById(HttpServletRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @PreAuthorize("fileRole(#request)")
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> findCategoryByName(HttpServletRequest request, @PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }
}