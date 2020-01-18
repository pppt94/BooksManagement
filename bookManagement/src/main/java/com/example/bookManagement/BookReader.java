package com.example.bookManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class BookReader implements Serializable {

    private @Id
    @GeneratedValue
    Long id;


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Book book;


    @ManyToOne
    @JoinColumn
    private User reader;

    private int mark;
    private int year;

    BookReader() {
    }

    BookReader(Book book, User reader,  int year, int mark) {
        this.book = book;
        this.reader = reader;
        this.mark = mark;
        this.year = year;
    }
}
