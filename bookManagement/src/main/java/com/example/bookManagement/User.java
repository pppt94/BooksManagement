package com.example.bookManagement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String login;
    private String password;

    User() {
    }

    User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
