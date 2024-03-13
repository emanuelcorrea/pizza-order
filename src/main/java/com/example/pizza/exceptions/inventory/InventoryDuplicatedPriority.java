package com.example.pizza.exceptions.inventory;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class InventoryDuplicatedPriority extends RuntimeException {
    private final int priority;

    public InventoryDuplicatedPriority(int priority) {
        super(String.format("Priority '%s' is duplicated.", priority));
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }
}
