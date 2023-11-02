package com.example.pizza.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(@NotBlank String name) {
}
