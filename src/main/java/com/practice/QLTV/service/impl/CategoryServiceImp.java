package com.practice.QLTV.service.impl;


import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.entity.Category;
import com.practice.QLTV.repository.CategoryRepository;
import com.practice.QLTV.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .categoryCode(categoryDTO.getCategoryCode())
                .categoryName(categoryDTO.getCategoryName())
                .description(categoryDTO.getDescription())
                .build();
        categoryRepository.save(category);
        return findAllCategories();
    }

    @Override
    public Category updateCategory(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategoryCode(categoryDTO.getCategoryCode());
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public List<Category> deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return findAllCategories();
    }

    @Override
    public List<Category> addMultipleCategories(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<Category> categories = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            Category category = Category.builder()
                    .categoryCode(row.getCell(0).getStringCellValue())
                    .categoryName(row.getCell(1).getStringCellValue())
                    .description(row.getCell(2).getStringCellValue())
                    .build();
            categories.add(category);
        }
        workbook.close();
        categoryRepository.saveAll(categories);
        return findAllCategories();
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .map(List::of).orElse(List.of());
    }
}
