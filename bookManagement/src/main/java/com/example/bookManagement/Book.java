package com.example.bookManagement;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book {

    private @Id
    @GeneratedValue
    Long id;
    private String title;
    private String category;
    @JsonIgnore
    @ManyToMany
    private List<Author> authors = new ArrayList<>();
    private String publisher;
    private int year;
    @JsonIgnore
    @ManyToMany
    private List<User> users = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<User> usersWishlist = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BookReader> bookReaders;

    Book() {
    }

    Book(String title, String category, Author author, String publisher, int year) {
        this.title = title;
        this.category = category;
        this.authors.add(author);
        this.publisher = publisher;
        this.year = year;
    }


    Book(String title, String category, Author author, String publisher, int year, User user) {
        this.title = title;
        this.category = category;
        this.authors.add(author);
        this.publisher = publisher;
        this.year = year;
        this.users.add(user);
    }

    void addUser(User user) {
        this.users.add(user);
        //user.getBooks().add(this);
    }

    void addUserWishlist(User user) {
        this.getUsersWishlist().add(user);
        //user.getBooks().add(this);
    }

    @JsonGetter
    public List<String> getAuthorsNames() {

        List<String> authorsNames = new ArrayList<>();

        for(Author author:authors) {
            authorsNames.add(author.getName());
        }
        return authorsNames;
    }

    @JsonGetter
    public List<Long> getAuthorsId() {

        List<Long> authorsId = new ArrayList<>();

        for(Author author:authors) {
            authorsId.add(author.getId());
        }
        return authorsId;
    }
}