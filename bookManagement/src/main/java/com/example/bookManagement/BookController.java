package com.example.bookManagement;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/user/info")
    User infoUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        return u;
    }

    @GetMapping("/user/books/wishlist")
    List<Book> allWishlistByLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        return repository.findByUsersWishlistId(u.getId());
    }

    @GetMapping("/books/read")
    List<Book> readBook() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        return repository.findByBookReadersReaderId(u.getId());
    }

    @GetMapping("/books/read/stats")
    List<List<Integer>> readBookstats() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());

        List<Book> listOfBook = repository.findByBookReadersReaderId(u.getId());
        List<Integer> listOfYear = new ArrayList();
        List<Integer> data = new ArrayList();
        List<Integer> column = new ArrayList();

        for (Book book:listOfBook) {
            for (BookReader bookReader:book.getBookReaders()) {
                listOfYear.add(bookReader.getYear());
            }
        }
        Map<Integer, Long> counts =
                listOfYear.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));


        for (int year:listOfYear) {
            if(column.contains(year)) {
                continue;
            }
            column.add(year);
            data.add(counts.get(year).intValue());
        }
        List<List<Integer>> list = new ArrayList<>();
        list.add(column);
        list.add(data);

        return list;
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
            repository.save(book);
        }
        return new Book();
    }

    @PostMapping("/books/towishist")
    Book newWishlistBook(@RequestBody BookDto newBook) {


        if(authorRepository.findAuthorByName(newBook.getAuthor()).isEmpty()) {
            authorRepository.save(new Author(newBook.getAuthor()));
        }

        Author author = authorRepository.findAuthorByName(newBook.getAuthor()).get(0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());


        if(repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).isEmpty()) {
            Book book = new Book(newBook.getTitle(), newBook.getCategory(), author, newBook.getPublisher(), newBook.getYear());
            book.addUserWishlist(u);
            repository.save(book);
        } else {
            Book book = repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).get(0);
            book.addUserWishlist(u);
            repository.save(book);
        }
        return new Book();
    }

    @PostMapping("/books/read")
    Book newReadBook(@RequestBody BookDto newBook) {


        if(authorRepository.findAuthorByName(newBook.getAuthor()).isEmpty()) {
            authorRepository.save(new Author(newBook.getAuthor()));
        }

        Author author = authorRepository.findAuthorByName(newBook.getAuthor()).get(0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());


        if(repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).isEmpty()) {
            Book book = new Book(newBook.getTitle(), newBook.getCategory(), author, newBook.getPublisher(), newBook.getYear());
            book.getBookReaders().add(new BookReader(book, u, newBook.getReading(), newBook.getMark()));
            repository.save(book);
        } else {
            Book book = repository.findByTitleAndPublisher(newBook.getTitle(), newBook.getPublisher()).get(0);
            book.getBookReaders().add(new BookReader(book, u, newBook.getReading(), newBook.getMark()));
            repository.save(book);
        }
        return new Book();
    }




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

    @DeleteMapping("/books/wishlist/{id}")
    void deleteWishlistBook(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());
        Book book = repository.findById(id).get();

        u.getWishlistBooks().remove(book);
        book.getUsersWishlist().remove(u);

        repository.save(book);
    }

    @GetMapping("/books/wishlist/add/{id}")
    void moveWishlistBook(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.findByEmail(userDetail.getUsername());
        Book book = repository.findById(id).get();

        u.getWishlistBooks().remove(book);
        book.getUsersWishlist().remove(u);
        u.getBooks().add(book);
        book.getUsers().add(u);

        repository.save(book);
    }
}