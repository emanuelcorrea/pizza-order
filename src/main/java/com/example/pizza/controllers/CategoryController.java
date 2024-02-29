package com.example.pizza.controllers;

import com.example.pizza.dtos.CategoryDto;
import com.example.pizza.models.Category;
import com.example.pizza.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Operation(summary = "Retrieve a list of categories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of categories")
    })
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Create a category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new category"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid category data")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryRecord) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRecord, category);

        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
}
