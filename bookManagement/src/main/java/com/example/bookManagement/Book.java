package com.example.bookManagement;


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
    @JsonIgnore
    @ManyToMany
    private List<Author> authors = new ArrayList<>();

    Book() {
    }

    Book(String title, Author author) {
        this.title = title;
        this.authors.add(author);
    }
}