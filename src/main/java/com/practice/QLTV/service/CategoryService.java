package com.practice.QLTV.service;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> createCategory(CategoryDTO categoryDTO);

    Category updateCategory(Integer id, CategoryDTO categoryDTO);

    List<Category> deleteCategory(Integer id);

    List<Category> addMultipleCategories(MultipartFile file) throws IOException;

    List<Category> findAllCategories();

    Category findCategoryById(Integer id);

    List<Category> findCategoryByName(String categoryName);
}

