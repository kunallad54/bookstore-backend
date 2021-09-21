package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;

import java.util.List;

public interface IBookService {

    /**
     * Purpose : Ability to add new books in book repository which will then save it database
     * if bookDTO data is valid
     *
     * @param bookDTO object of BookDTO taken from user
     * @return String Object of message whether it was able to add a book or not
     */
    String addBook(BookDTO bookDTO);

    /**
     * Purpose : To get all list of books available in book repository which it will fetch
     * it from database
     *
     * @return List of BookDTO objects from database i.e list of books in DB
     */
    List<BookDTO> getBooks();

    /**
     * Purpose : Ability to delete book from database with help of its ID
     *
     * @param id book id to delete particular book
     * @return String object of message whether it was deleted successfully or not
     */
    String deleteBook(int id);

    /**
     * Purpose : Ability to update book price with its updated book price with help of
     * book id
     *
     * @param id           book id to update particular book price
     * @param bookPriceDTO object of BookPriceDTO which sets new updated price of book
     * @return String object of message whether book price was updated or not
     */
    String updateBookPrice(int id, BookPriceDTO bookPriceDTO);

    /**
     * Purpose : Ability to update book quanity with its updated book quantity with help of
     * book id
     *
     * @param id              book id to update particular book quantity
     * @param bookQuantityDTO object of BookQuantityDTO which sets new updated quantiy of book
     * @return String object of message whether book quantity was updated or not
     */
    String updateBookQuantity(int id, BookQuantityDTO bookQuantityDTO);
}
