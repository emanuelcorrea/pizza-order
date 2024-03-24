package com.example.pizza.exceptions.product;

import com.example.pizza.interfaces.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class ProductNotFoundException extends RuntimeException implements CustomException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ProductNotFoundException(UUID productId) {
        super("The product with ID " + productId + " was not found.");
    }
}
