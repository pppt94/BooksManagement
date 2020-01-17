package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReaderRepository extends JpaRepository<BookReader, Long>{
}