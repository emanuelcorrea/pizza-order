package com.example.pizza.controllers;

import com.example.pizza.dtos.CategoryDto;
import com.example.pizza.dtos.ProductDto;
import com.example.pizza.models.Category;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.CategoryRepository;
import com.example.pizza.repositories.ProductRepository;
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
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @PostMapping("/products")
    public ResponseEntity<Object> create(@RequestBody @Valid ProductDto productRecord) {
        Product product = new Product();

        BeanUtils.copyProperties(productRecord, product);

        UUID categoryId = UUID.fromString(productRecord.category_id());

        System.out.println(categoryId);
        var productCreated = categoryRepository.findById(categoryId).map(category -> {
            product.setCategory(category);

            return productRepository.save(product);
        });

        return ResponseEntity.status(HttpStatus.OK).body(productCreated);
    }

}
