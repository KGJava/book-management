package com.henry.bookmanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.henry.bookmanagement.model.Book;
import com.henry.bookmanagement.model.Branch;
import com.henry.bookmanagement.model.Inventory;
import com.henry.bookmanagement.repository.BookRepository;
import com.henry.bookmanagement.repository.BranchRepository;
import com.henry.bookmanagement.repository.InventoryRepository;

/**
 * Created by Swapnil Sundarkar
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	BookRepository bookRepository;

	@GetMapping("/")
	public List<Inventory> getAllInventories() {
		List<Inventory> inventories = inventoryRepository.findAll();
		this.populateNames(inventories);
		return inventories;
	}

	@PostMapping("/")
	public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@GetMapping("/{id}")
	public Inventory getInventoryById(@PathVariable(value = "id") Long inventoryId) {
		Inventory inventory = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("InventoryId", "id", inventoryId));
		Book book = bookRepository.findById(inventory.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("book", "id", inventory.getBookId()));
		inventory.setBookTitle(book.getTitle());
		Branch branch = branchRepository.findById(inventory.getBranchId())
				.orElseThrow(() -> new ResourceNotFoundException("BranchId", "id", inventory.getBranchId()));
		inventory.setBranchName(branch.getBranchName());
		return inventory;
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

	private void populateNames(List<Inventory> inventories) {
		List<Book> books = bookRepository.findAll();
		List<Branch> branches = branchRepository.findAll();
		Map<Long, String> bookIdToBookTitleMap = new HashMap<>();
		Map<Long, String> branchIdToBranchTitleMap = new HashMap<>();
		for (Book book : books) {
			bookIdToBookTitleMap.put(book.getId(), book.getTitle());
		}
		for (Branch branch : branches) {
			branchIdToBranchTitleMap.put(branch.getId(), branch.getBranchName());
		}
		for (Inventory inventory : inventories) {
			inventory.setBookTitle(bookIdToBookTitleMap.get(inventory.getBookId()));
			inventory.setBranchName(branchIdToBranchTitleMap.get(inventory.getBranchId()));
		}
	}
}
