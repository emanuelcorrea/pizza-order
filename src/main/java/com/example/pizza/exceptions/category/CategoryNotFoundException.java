package com.example.pizza.exceptions.category;

import com.example.pizza.interfaces.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class CategoryNotFoundException extends RuntimeException implements CustomException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public CategoryNotFoundException(UUID categoryId) {
        super("The category with ID " + categoryId + " was not found.");
    }
}
