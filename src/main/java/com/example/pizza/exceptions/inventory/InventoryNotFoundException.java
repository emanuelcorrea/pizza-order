package com.example.pizza.exceptions.inventory;

import java.util.UUID;

public class InventoryNotFoundException extends RuntimeException {
    private final UUID id;

    public InventoryNotFoundException(UUID id) {
        super("Inventory with id " + id + " not found.");

        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
