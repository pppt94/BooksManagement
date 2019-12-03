package com.example.bookManagement;

public class BookNotFoundException extends RuntimeException {

    BookNotFoundException(Long id) {
        super("Could not find Book " + id);
    }

}
