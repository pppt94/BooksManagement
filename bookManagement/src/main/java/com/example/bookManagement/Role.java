package com.example.bookManagement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Role {

    @Id
    private int id;
    private String role;
}
