package com.example.bookManagement;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {

        return args -> {
            Author aut = new Author("J.R.R. Tolkien");
            authorRepository.save(aut);
            bookRepository.save(new Book("Lord of The Rings", "fantasy", aut));
        };
    }
}
