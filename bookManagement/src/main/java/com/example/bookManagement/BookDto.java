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
public class BookDto {

    private @Id
    @GeneratedValue
    Long id;
    private String title;
    private String category;
    private String  author;
    private String publisher;
    private int year;

    BookDto() {
    }

}