package com.example.bookManagement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class BookReader implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn
    private User reader;

    private int mark;
    private int year;

    BookReader() {
    }

    BookReader(Book book, User reader, int mark, int year) {
        this.book = book;
        this.reader = reader;
        this.mark = mark;
        this.year = year;
    }
}
