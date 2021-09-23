package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull(message = "Quantity cannot be null")
    private int quantity;

    private String city;

    private String state;

    @NotNull(message = "Address cannot be null")
    private String address;

    private String zipCode;

    @NotNull(message = "Book id cannot be null")
    private int bookId;

}
