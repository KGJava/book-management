package com.henry.bookmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.bookmanagement.exception.ResourceNotFoundException;
import com.henry.bookmanagement.model.Inventory;
import com.henry.bookmanagement.repository.InventoryRepository;

/**
 * Created by Swapnil Sundarkar
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryRepository inventoryRepository;

	@GetMapping("/")
	public List<Inventory> getAllInventorys() {
		return inventoryRepository.findAll();
	}

	@PostMapping("/")
	public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@GetMapping("/{id}")
	public Inventory getInventoryById(@PathVariable(value = "id") Long inventoryId) {
		return inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("InventoryId", "id", inventoryId));
	}

	@PutMapping("/{id}")
	public Inventory updateInventory(@PathVariable(value = "id") Long inventoryId,
			@Valid @RequestBody Inventory inventoryDetails) {

		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", inventoryId));

		inventory.setQuantity(inventoryDetails.getQuantity());

		Inventory updatedInventory = inventoryRepository.save(inventory);
		return updatedInventory;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInventory(@PathVariable(value = "id") Long inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", inventoryId));

		inventoryRepository.delete(inventory);

		return ResponseEntity.ok().build();
	}
}
