package com.example.pizza.controllers;

import com.example.pizza.dtos.InventoryDto;
import com.example.pizza.exceptions.inventory.InventoryDuplicatedPriority;
import com.example.pizza.exceptions.inventory.InventoryNotFoundException;
import com.example.pizza.models.Inventory;
import com.example.pizza.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/inventories", produces = "application/json")
@Tag(name = "Inventories", description = "Inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) { this.inventoryService = inventoryService; }

    @Operation(summary = "Retrieve a list of inventories", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of inventories"),
    })
    @GetMapping
    public ResponseEntity<List<Inventory>> findAll() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @Operation(summary = "Retrieve a single inventory by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the inventory"),
            @ApiResponse(responseCode = "404", description = "Inventory not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> findOne(@PathVariable String id) {
        try {
            UUID inventoryId = UUID.fromString(id);
            Inventory inventory = inventoryService.findOne(inventoryId);

            return ResponseEntity.ok(inventory);
        } catch (InventoryNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Create a new inventory", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the inventory"),
            @ApiResponse(responseCode = "409", description = "Priority '1' is duplicated.")
    })
    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody @Valid InventoryDto inventoryRecord) {
        try {
            Inventory inventory = new Inventory();
            BeanUtils.copyProperties(inventoryRecord, inventory);

            Inventory savedInventory = inventoryService.create(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
        } catch (InventoryDuplicatedPriority ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Update an inventory by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the inventory"),
            @ApiResponse(responseCode = "404", description = "Inventory not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> update(@PathVariable String id, @RequestBody @Valid InventoryDto inventoryRecord) {
        UUID inventoryId = UUID.fromString(id);
        Inventory inventory = inventoryService.findOne(inventoryId);

        BeanUtils.copyProperties(inventoryRecord, inventory);

        Inventory savedInventory = inventoryService.update(inventory);
        return ResponseEntity.ok(savedInventory);
    }
}
