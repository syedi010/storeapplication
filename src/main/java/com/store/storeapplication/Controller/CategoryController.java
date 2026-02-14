package com.store.storeapplication.Controller;

import com.store.storeapplication.Entity.Category;
import com.store.storeapplication.Service.CategoryService;
import com.store.storeapplication.Comman.BusinessCode;
import com.store.storeapplication.DTO.ApiRequest;
import com.store.storeapplication.DTO.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor Injection (Best Practice)
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {

        List<Category> allCategories = categoryService.getAllCategories();

        if (allCategories.isEmpty()) {
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(false, "No categories found", BusinessCode.NOT_FOUND, allCategories));
        }
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Categories fetched successfully", BusinessCode.SUCCESS, allCategories));
    }

    // POST add category
    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody ApiRequest<Category> request) {

        Category category = request.getData();

        if (category.getName() == null || category.getName().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Category name cannot be empty", BusinessCode.VALIDATION_ERROR, null));
        }
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Category added successfully", BusinessCode.SUCCESS, savedCategory));
    }

    @PostMapping("/getCategoryById/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        return new ApiResponse<>(true, "Category fetched successfully", BusinessCode.SUCCESS,
                categoryService.getCategoryById(id));
    }

    @PostMapping("/deleteCategory")
    public ApiResponse<Void> deleteCategory(@RequestBody Category category) {
        long id = category.getId();
        categoryService.deleteCategory(id);
        return new ApiResponse<>(true, "Category deleted successfully", BusinessCode.SUCCESS, null);
    }
}
