package com.example.pizza.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(@NotBlank String title, String description, @NotNull BigDecimal value,
                         @NotNull String category_id, @NotNull int quantity) {
}
