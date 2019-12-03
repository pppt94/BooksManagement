package com.example.bookManagement;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Author {

    private @Id
    @GeneratedValue
    Long id;
    private String first_name;
    private String surname;
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    Author() {
    }

    Author(String first_name, String surname) {
        this.first_name = first_name;
        this.surname = surname;
    }

    @JsonGetter
    public List<String> getBooksNames() {

        List<String> booksNames = new ArrayList<>();

        for(Book book:books) {
            booksNames.add(book.getTitle());
        }
        return booksNames;
    }

    @JsonGetter
    public List<Long> getBooksId() {

        List<Long> booksId = new ArrayList<>();

        for(Book book:books) {
            booksId.add(book.getId());
        }
        return booksId;
    }
}
