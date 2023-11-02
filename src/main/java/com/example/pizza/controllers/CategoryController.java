package com.example.pizza.controllers;

import com.example.pizza.dtos.CategoryDto;
import com.example.pizza.models.Category;
import com.example.pizza.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryRecord) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryRecord, category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.save(category));
    }
}
