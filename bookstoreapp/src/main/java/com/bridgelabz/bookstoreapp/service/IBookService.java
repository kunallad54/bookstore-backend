package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;

import java.util.List;

public interface IBookService {

    String addBook(BookDTO bookDTO);

    List<BookDTO> getBooks();

    String deleteBook(int id);

    String updateBookPrice(int id, BookPriceDTO bookPriceDTO);

    String updateBookQuantity(int id, BookQuantityDTO bookQuantityDTO);
}
