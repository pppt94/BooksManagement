package com.example.bookManagement;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

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

    Book() {
    }

    Book(String title, String category, Author author, String publisher, int year) {
        this.title = title;
        this.category = category;
        this.authors.add(author);
        this.publisher = publisher;
        this.year = year;
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