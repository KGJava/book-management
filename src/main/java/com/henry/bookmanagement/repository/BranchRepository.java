package com.henry.bookmanagement.repository;

import com.henry.bookmanagement.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 Created by Swapnil Sundarkar
 */

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

}
