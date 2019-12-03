package com.example.bookManagement;

public class AuthorNotFoundException extends RuntimeException {

    AuthorNotFoundException(Long id) {
        super("Could not find author " + id);
    }

}
