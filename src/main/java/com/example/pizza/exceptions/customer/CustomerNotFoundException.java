package com.example.pizza.exceptions.customer;

import com.example.pizza.interfaces.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class CustomerNotFoundException extends RuntimeException implements CustomException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public CustomerNotFoundException(UUID customerId) {
        super("The customer with ID " + customerId + " was not found.");
    }
}
