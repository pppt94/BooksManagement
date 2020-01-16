package com.example.bookManagement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookController {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    BookController(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    List<Book> all() {
        return repository.findAll();
    }

    @PostMapping("/books")
    Book newBook(@RequestBody BookDto newBook) {


        if(authorRepository.findAuthorByName(newBook.getAuthor()).isEmpty()) {
            authorRepository.save(new Author(newBook.getAuthor()));
        }

        Author author = authorRepository.findAuthorByName(newBook.getAuthor()).get(0);

        Book book = new Book(newBook.getTitle(), newBook.getCategory(), author, newBook.getPublisher(), newBook.getYear());

        return repository.save(book);
    }

    // Single item


    @GetMapping("/publishers")
    List<String> newBook() {
        return repository.findPublishers();
    }

    @GetMapping("/books/{id}")
    Book one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/books/{id}")
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

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}