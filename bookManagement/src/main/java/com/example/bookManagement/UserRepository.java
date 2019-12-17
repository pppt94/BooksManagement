package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Book, Long> {

    User findByEmail(String email);
}
