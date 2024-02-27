package com.example.pizza.dtos;

import jakarta.validation.constraints.NotNull;

public record CustomerDto(@NotNull String email, @NotNull String password, @NotNull String name) {
}
