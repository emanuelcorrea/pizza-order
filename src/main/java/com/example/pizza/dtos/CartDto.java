package com.example.pizza.dtos;

import com.example.pizza.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartDto(@NotNull String customer_id, List<Product> items) {
}
