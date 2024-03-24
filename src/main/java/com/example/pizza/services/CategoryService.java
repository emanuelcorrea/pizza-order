package com.example.pizza.services;

import com.example.pizza.dtos.CategoryDto;
import com.example.pizza.exceptions.category.CategoryNotFoundException;
import com.example.pizza.models.Category;
import com.example.pizza.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findOne(String id) {
        Optional<Category> optionalCategory = categoryRepository.findById(UUID.fromString(id));

        if (optionalCategory.isEmpty())
            throw new CategoryNotFoundException(UUID.fromString(id));

        return optionalCategory.get();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category create(CategoryDto categoryRecord) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRecord, category);

        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = findOne(id);

        categoryRepository.deleteById(category.getId());
    }

    public Category update(String id, CategoryDto categoryRecord) {
        Category category = findOne(id);

        BeanUtils.copyProperties(categoryRecord, category);

        return categoryRepository.save(category);
    }
}
