package com.example.bookManagement;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookController {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final UserService userService;

    BookController(BookRepository repository, AuthorRepository authorRepository, UserService userService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.userService = userService;
    }

    @GetMapping("/books")
    List<Book> all() {
        return repository.findAll();
    }

    @GetMapping("/user/books/{id}")
    List<Book> allByUser(@PathVariable Long id) {
        return repository.findByUsersId(id);
    }

    @GetMapping("/user/books")
    List<Book> allByLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        return repository.findByUsersId(u.getId());
    }


    @PostMapping("/books")
    Book newBook(@RequestBody BookDto newBook) {


        if(authorRepository.findAuthorByName(newBook.getAuthor()).isEmpty()) {
            authorRepository.save(new Author(newBook.getAuthor()));
        }

        Author author = authorRepository.findAuthorByName(newBook.getAuthor()).get(0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        System.out.println(u);

        if(repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).isEmpty()) {
            Book book = new Book(newBook.getTitle(), newBook.getCategory(), author, newBook.getPublisher(), newBook.getYear());

            book.addUser(u);
            repository.save(book);
        } else {
            Book book = repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).get(0);
            book.addUser(u);

            System.out.println(u);
            System.out.println(book);
            repository.save(book);
        }


        return new Book();
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());
        Book book = repository.findById(id).get();

        u.getBooks().remove(book);
        book.getUsers().remove(u);

        repository.save(book);
    }
}