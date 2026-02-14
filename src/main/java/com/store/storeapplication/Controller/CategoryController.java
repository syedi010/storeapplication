package com.store.storeapplication.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.storeapplication.Comman.BusinessCode;
import com.store.storeapplication.DTO.ApiRequest;
import com.store.storeapplication.DTO.ApiResponse;
import com.store.storeapplication.DTO.fetchIdenticals;
import com.store.storeapplication.Entity.Category;
import com.store.storeapplication.Service.CategoryService;

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
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Category fetched successfully", BusinessCode.SUCCESS,
                categoryService.getCategoryById(id)));
    }

    @PostMapping("/deleteCategory")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@RequestBody Category category) {
        long id = category.getId();
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, "Category deleted successfully", BusinessCode.SUCCESS, null));
    }

    @GetMapping("/fetchIdenticals")
    
    public ResponseEntity<fetchIdenticals<Set<String>>> fetchIdenticals()
    {   
     Set<String> uniqueNames=categoryService.getAllCategories().stream().map(Category::getName).collect(Collectors.toSet());

    return ResponseEntity.ok(new fetchIdenticals<>(true,"Unique category name found", 0,uniqueNames,uniqueNames.size()));
    }

    @GetMapping("/totalCategoryCount")

    public ResponseEntity<ApiResponse<Long>> fetchTotalCategoryCount(){

        long totalCount=categoryService.getAllCategories().size();

        return ResponseEntity.ok().body(new ApiResponse<>(true,"Total Count fetched", 0, totalCount));
    }
}
