package com.example.pizza.config;

import com.example.pizza.exceptions.ExceptionResponse;
import com.example.pizza.exceptions.category.CategoryNotFoundException;
import com.example.pizza.exceptions.product.ProductsNotFoundException;
import com.example.pizza.interfaces.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductsNotFoundException.class)
    private ResponseEntity<ExceptionResponse> productsNotFound(ProductsNotFoundException exception) {
        return this.buildResponse(exception);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<ExceptionResponse> categoryNotFound(CategoryNotFoundException exception) {
        return this.buildResponse(exception);
    }

    private ResponseEntity<ExceptionResponse> buildResponse(CustomException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getStatus(), exception.getMessage());

        return new ResponseEntity<>(response, exception.getStatus());
    }
}
