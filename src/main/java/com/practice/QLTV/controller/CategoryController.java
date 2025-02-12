package com.practice.QLTV.controller;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.entity.Category;
import com.practice.QLTV.service.CategoryService;
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
    public ResponseEntity<List<Category>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Category>> addMultipleCategories(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(categoryService.addMultipleCategories(file));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Category>> findCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }
}
