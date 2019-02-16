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
import com.henry.bookmanagement.model.Branch;
import com.henry.bookmanagement.repository.BranchRepository;

/**
 * Created by Swapnil Sundarkar
 */
@RestController
@RequestMapping("/branches")
public class BranchController {

	@Autowired
	BranchRepository branchRepository;

	@GetMapping("/")
	public List<Branch> getAllBranch() {
		return branchRepository.findAll();
	}

	@PostMapping("/")
	public Branch createBranch(@Valid @RequestBody Branch branch) {
		return branchRepository.save(branch);
	}

	@GetMapping("/{id}")
	public Branch getNoteById(@PathVariable(value = "id") Long branchId) {
		return branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("BranchId", "id", branchId));
	}

	@PutMapping("/{id}")
	public Branch updateNote(@PathVariable(value = "id") Long branchId, @Valid @RequestBody Branch branchDetails) {

		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));

		branch.setAddress(branchDetails.getAddress());
		branch.setBranchName(branchDetails.getBranchName());
		branch.setCity(branchDetails.getCity());
		branch.setPhone(branchDetails.getPhone());
		branch.setState(branchDetails.getState());
		branch.setZip(branchDetails.getZip());
		
		Branch updatedBranch = branchRepository.save(branch);
		return updatedBranch;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long branchId) {
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", branchId));

		branchRepository.delete(branch);

		return ResponseEntity.ok().build();
	}
}
