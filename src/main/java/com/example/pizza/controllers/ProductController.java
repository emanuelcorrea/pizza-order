package com.example.pizza.controllers;

import com.example.pizza.dtos.ProductDto;
import com.example.pizza.models.Product;
import com.example.pizza.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = "application/json")
@Tag(name = "Products", description = "Products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Retrieve a list of products", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products")
    })
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Retrieve a product by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable String id) {
        Product product = productService.findById(id);

        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Create a product", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new product"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ProductDto productRecord) {
        Product createdProduct = productService.create(productRecord);

        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "Update a product by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid ProductDto productRecord) {
        Product updatedProduct = productService.update(id, productRecord);

        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Delete a product by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
