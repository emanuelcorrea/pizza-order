package com.example.pizza.services;

import com.example.pizza.exceptions.inventory.InventoryDuplicatedPriority;
import com.example.pizza.exceptions.inventory.InventoryNotFoundException;
import com.example.pizza.models.Inventory;
import com.example.pizza.repositories.InventoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Inventory findOne(UUID id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));
    }

    public Inventory create(Inventory inventory) {
        try {
            return inventoryRepository.save(inventory);
        } catch (DataIntegrityViolationException e) {
            throw new InventoryDuplicatedPriority(inventory.getPriority());
        }
    }

    public Inventory update(Inventory inventory) {
        try {
            return inventoryRepository.save(inventory);
        } catch (DataIntegrityViolationException e) {
            throw new InventoryDuplicatedPriority(inventory.getPriority());
        }

    }
}
