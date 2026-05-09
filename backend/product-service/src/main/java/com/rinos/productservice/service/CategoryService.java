package com.rinos.productservice.service;

import com.rinos.productservice.dto.CategoryDTO;
import com.rinos.productservice.entity.Category;
import com.rinos.productservice.exception.ProductNotFoundException;
import com.rinos.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Category not found with id: " + id));
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setStatus(categoryDTO.getStatus());

        Category savedCategory = categoryRepository.save(category);

        return convertToCategoryDTO(savedCategory);
    }

    public CategoryDTO updateCategory(long id, CategoryDTO categoryDTO) {
        Category category = getCategoryById(id);

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getStatus() != null) {
            category.setStatus(categoryDTO.getStatus());
        }

        category.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(category);

        return convertToCategoryDTO(updatedCategory);
    }

    public void deleteCategory(long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setStatus(category.getStatus());
        return categoryDTO;
    }
}
