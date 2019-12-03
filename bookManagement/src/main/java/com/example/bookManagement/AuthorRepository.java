package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AuthorRepository extends JpaRepository<Author, Long>{
}
