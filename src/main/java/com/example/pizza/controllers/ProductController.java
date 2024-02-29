package com.example.pizza.controllers;

import com.example.pizza.dtos.ProductDto;
import com.example.pizza.models.Category;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CategoryRepository;
import com.example.pizza.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
@Tag(name = "Products", description = "Products")
public class ProductController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Operation(summary = "Retrieve a list of products", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products")
    })
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @Operation(summary = "Create a product", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new product"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ProductDto productRecord) {
        Product product = new Product();
        BeanUtils.copyProperties(productRecord, product);

        UUID categoryId = UUID.fromString(productRecord.category_id());

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Category category = optionalCategory.get();

        product.setCategory(category);

        return ResponseEntity.ok(productRepository.save(product));
    }

    @Operation(summary = "Update a product by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid ProductDto productRecord) {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();
        BeanUtils.copyProperties(productRecord, product);

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
}
