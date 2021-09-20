package com.bridgelabz.bookstoreapp.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BOOK")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name="LOGO")
    private String logo;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "QUANTITY")
    private int quantity;
}
