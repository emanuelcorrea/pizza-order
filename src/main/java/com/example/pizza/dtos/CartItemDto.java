package com.example.pizza.dtos;

import com.example.pizza.models.CartItem;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartItemDto(@NotNull List<CartItem> items) {
}
