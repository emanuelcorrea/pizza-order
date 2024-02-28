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

    @Autowired
    CategoryRepository categoryRepository;

    @Operation(summary = "Retrieves a list of categories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

    @Operation(summary = "Creates a category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryRecord) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryRecord, category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.save(category));
    }
}
