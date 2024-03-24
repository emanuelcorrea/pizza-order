package com.example.pizza.controllers;

import com.example.pizza.dtos.CategoryDto;
import com.example.pizza.exceptions.category.CategoryNotFoundException;
import com.example.pizza.models.Category;
import com.example.pizza.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Retrieve a list of categories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of categories")
    })
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Retrieve a single category by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> findOne(@PathVariable String id) {
        Category category = categoryService.findOne(id);

        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Create a category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new category")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryRecord) {
        Category createdCategory = categoryService.create(categoryRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Operation(summary = "Update a category", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable String id, @RequestBody @Valid CategoryDto categoryRecord) {
        Category updatedCategory = categoryService.update(id, categoryRecord);

        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Delete a category", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the category"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable String id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
