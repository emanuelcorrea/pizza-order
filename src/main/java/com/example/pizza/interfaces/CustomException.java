package com.example.pizza.interfaces;

import org.springframework.http.HttpStatus;

public interface CustomException {
    HttpStatus getStatus();
    String getMessage();
}
