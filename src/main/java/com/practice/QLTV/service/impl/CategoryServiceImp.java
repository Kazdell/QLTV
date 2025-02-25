package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.CategoryDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.Category;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse<List<CategoryDTO>> createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).isPresent()) {
            throw new AppException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Category with name " + categoryDTO.getCategoryName() + " already exists");
        }
        Category category = Category.builder()
                .categoryCode(categoryDTO.getCategoryCode())
                .categoryName(categoryDTO.getCategoryName())
                .description(categoryDTO.getDescription())
                .build();
        categoryRepository.save(category);
        List<CategoryDTO> allCategories = getAllCategoriesAsDTO();
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message("Category created successfully")
                .result(allCategories)
                .build();
    }

    @Override
    public ApiResponse<CategoryDTO> updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        category.setCategoryCode(categoryDTO.getCategoryCode());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category = categoryRepository.save(category);
        CategoryDTO result = toCategoryDTO(category);
        return ApiResponse.<CategoryDTO>builder()
                .code(ErrorCode.USER_UPDATED_SUCCESSFULLY.getCode()) // 1000
                .message("Category updated successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<CategoryDTO>> deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        categoryRepository.delete(category);
        List<CategoryDTO> allCategories = getAllCategoriesAsDTO();
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Category deleted successfully")
                .result(allCategories)
                .build();
    }

    @Override
    public ApiResponse<List<CategoryDTO>> addMultipleCategories(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<Category> categories = new ArrayList<>();
        int rowNum = 0;

        for (Row row : sheet) {
            if (rowNum++ == 0) continue; // Skip header
            String categoryName = row.getCell(1).getStringCellValue();
            if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
                continue; // Skip duplicates
            }
            Category category = Category.builder()
                    .categoryCode(row.getCell(0).getStringCellValue())
                    .categoryName(categoryName)
                    .description(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null)
                    .build();
            categories.add(category);
        }
        workbook.close();
        categoryRepository.saveAll(categories);
        List<CategoryDTO> allCategories = getAllCategoriesAsDTO();
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message("Categories created successfully")
                .result(allCategories)
                .build();
    }

    @Override
    public ApiResponse<List<CategoryDTO>> findAllCategories() {
        List<CategoryDTO> categories = getAllCategoriesAsDTO();
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(categories)
                .build();
    }

    @Override
    public ApiResponse<CategoryDTO> findCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        CategoryDTO result = toCategoryDTO(category);
        return ApiResponse.<CategoryDTO>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<CategoryDTO>> findCategoryByName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        CategoryDTO result = toCategoryDTO(category);
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(List.of(result))
                .build();
    }

    private List<CategoryDTO> getAllCategoriesAsDTO() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setCategoryCode(category.getCategoryCode());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}