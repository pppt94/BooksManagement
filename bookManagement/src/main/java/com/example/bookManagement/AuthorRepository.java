package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>{

    List<Author> findAuthorByName(String name);
}
