package com.example.pizza.services;

import com.example.pizza.dtos.ProductDto;
import com.example.pizza.exceptions.product.ProductNotFoundException;
import com.example.pizza.models.Category;
import com.example.pizza.models.Product;
import com.example.pizza.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(id));

        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException(UUID.fromString(id));

        return optionalProduct.get();
    }

    public Product create(ProductDto productRecord) {
        Product product = new Product();
        BeanUtils.copyProperties(productRecord, product);

        Category category = categoryService.findOne(productRecord.category_id());

        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product update(String id, ProductDto productRecord) {
        Product product = findById(id);
        BeanUtils.copyProperties(productRecord, product);

        Category category = categoryService.findOne(productRecord.category_id());

        product.setCategory(category);

        return productRepository.save(product);
    }

    public void delete(String id) {
        Product product = findById(id);

        productRepository.delete(product);
    }
}
