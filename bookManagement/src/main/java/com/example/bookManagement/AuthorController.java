package com.example.bookManagement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class AuthorController {

    private final AuthorRepository repository;

    AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    List<Author> all() {
        return repository.findAll();
    }

    @PostMapping("/authors")
    Author newAuthor(@RequestBody Author newAuthor) {
        return repository.save(newAuthor);
    }

    // Single item

    @GetMapping("/authors/{id}")
    Author one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @PutMapping("/authors/{id}")
    Author replaceAuthor(@RequestBody Author newAuthor, @PathVariable Long id) {

        return repository.findById(id)
                .map(Author -> {
                    Author.setFirst_name(newAuthor.getFirst_name());
                    Author.setSurname(newAuthor.getSurname());
                    return repository.save(Author);
                })
                .orElseGet(() -> {
                    newAuthor.setId(id);
                    return repository.save(newAuthor);
                });
    }

    @DeleteMapping("/authors/{id}")
    void deleteAuthor(@PathVariable Long id) {
        repository.deleteById(id);
    }
}