package com.example.pizza.dtos;

import com.example.pizza.enums.Inventory.InventoryState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record InventoryDto(@NotNull String name, @NotNull Integer priority, @NotNull InventoryState state) {
}
