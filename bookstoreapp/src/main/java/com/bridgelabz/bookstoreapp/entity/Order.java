package com.bridgelabz.bookstoreapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "BOOK_ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    private int id;

    @Column(name = "RECEIVER_NAME")
        private String  receiverName;

    @Column(name = "RECEIVER_NUMBER")
    private String receiverNumber;

    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @Column(name = "BOOK_QUANTITY")
    private int quantity;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "LANDMARK")
    private String landMark;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    private Book book;

    @Column(name = "CANCEL")
    private boolean cancel;
}
