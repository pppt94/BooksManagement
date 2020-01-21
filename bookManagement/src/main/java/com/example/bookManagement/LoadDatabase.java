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
    CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository, UserService userService) {

        return args -> {


            /*User u = new User("Jan", "Kowalski", "a@a.a", "aaa");
            userService.save(u);


            Author aut1 = new Author("J.R.R. Tolkien");
            authorRepository.save(aut1);
            Author aut2 = new Author("George Orwell");
            authorRepository.save(aut2);
            Author aut3 = new Author("Joseph Heller");
            authorRepository.save(aut3);
            Author aut4 = new Author("Gabriel Garcia Marquez");
            authorRepository.save(aut4);
            Author aut5 = new Author("Andrzej Sapkowski");
            authorRepository.save(aut5);

            //Books
            bookRepository.save(new Book("Lord of The Rings", "fantasy", aut1, "CCC", 2001));
            bookRepository.save(new Book("Hobbit", "fantasy", aut1, "CCC", 2000));
            bookRepository.save(new Book("Nineteen Eighty-Four", "novel", aut2, "BBB", 2010));
            bookRepository.save(new Book("Animal Farm", "novel", aut2, "AAA", 2010));
            bookRepository.save(new Book("Catch-22", "novel", aut3, "AAA", 1990)); */
        };
    }
}
