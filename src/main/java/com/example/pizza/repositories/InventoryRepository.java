package com.example.pizza.repositories;

import com.example.pizza.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID>{
}
