package com.practice.QLTV.service;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    ApiResponse<List<CategoryDTO>> createCategory(CategoryDTO categoryDTO);

    ApiResponse<CategoryDTO> updateCategory(Integer id, CategoryDTO categoryDTO);

    ApiResponse<List<CategoryDTO>> deleteCategory(Integer id);

    ApiResponse<List<CategoryDTO>> addMultipleCategories(MultipartFile file) throws IOException;

    ApiResponse<List<CategoryDTO>> findAllCategories();

    ApiResponse<CategoryDTO> findCategoryById(Integer id);

    ApiResponse<List<CategoryDTO>> findCategoryByName(String categoryName);
}