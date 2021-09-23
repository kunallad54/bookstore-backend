package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private int id;
    @NotEmpty(message = "Book Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Book Name Invalid")
    private String name;

    @NotEmpty(message = "Author Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Author Name Invalid")
    private String author;

    @NotEmpty(message = "Description cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Description Invalid")
    private String description;

    @NotEmpty(message = "Logo cannot be null")
    private String logo;

    @NotNull(message = "Price cannot be null")
    private double price;

    @NotNull(message = "Quantity cannot be null")
    private int quantity;
}
