package com.example.bookManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByRole(String role);
}
