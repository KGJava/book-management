package com.henry.bookmanagement.repository;

import com.henry.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 Created by Swapnil Sundarkar
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
