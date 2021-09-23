package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {


    @NotNull(message = "Book Id cannot be null")
    private int bookId;

    @NotNull(message = "Quantity cannot be null")
    private int quantity;

}
