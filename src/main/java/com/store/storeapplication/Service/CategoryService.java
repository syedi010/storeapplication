package com.store.storeapplication.Service;

import com.store.storeapplication.Entity.Category;
import com.store.storeapplication.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor Injection (Best Practice)
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by id
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    // Delete category
    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found to delete category with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
