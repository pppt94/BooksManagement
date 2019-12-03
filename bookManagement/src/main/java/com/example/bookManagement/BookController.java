package com.example.bookManagement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/Books")
    List<Book> all() {
        return repository.findAll();
    }

    @PostMapping("/Books")
    Book newBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }

    // Single item

    @GetMapping("/Books/{id}")
    Book one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/Books/{id}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {

        return repository.findById(id)
                .map(Book -> {
                    Book.setTitle(newBook.getTitle());
                    return repository.save(Book);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    @DeleteMapping("/Books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}