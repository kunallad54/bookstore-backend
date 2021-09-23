package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Purpose : To add book in the database after validating book details
     *
     * @param token   generated token of user
     * @param bookDTO object of BookDTO which gets book data
     * @return String object of message
     */
    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestParam(name = "token") String token
            , @RequestBody @Valid BookDTO bookDTO) {
        log.info("Inside addBook Controller Method");
        return new ResponseEntity<>(bookService.addBook(token, bookDTO), HttpStatus.OK);
    }

    /**
     * Purpose : To get list of all books available in database
     *
     * @return List of BookDTO objects i.e list of books in DB
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getBooks(@RequestParam(name = "token") String token) {
        log.info("Inside getBooks Controller Method");
        List<BookDTO> bookList = bookService.getBooks(token);
        return new ResponseEntity<>(new ResponseDTO(messageSource.getMessage("get.books",
                null, Locale.ENGLISH), bookList), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to delete book from database with its book id
     *
     * @param id book id to delete that particular book taken from user
     * @return String object of message
     */
    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam(name = "id") int id, @RequestParam String token) {
        log.info("Inside deleteBook Controller Method");
        return new ResponseEntity<>(bookService.deleteBook(id, token), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to update book price with its book id
     *
     * @param id           book id to update book price of particular book
     * @param bookPriceDTO object of BookPriceDTO which sets new updated price of book
     * @return String object of message
     */
    @PutMapping("/updateBookPrice")
    public ResponseEntity<String> updateBookPrice(@RequestParam(name = "id") int id,
                                                  @RequestParam(name = "token") String token, @Valid
                                                  @RequestBody BookPriceDTO bookPriceDTO) {
        log.info("Inside updateBookPrice Controller Method");
        return new ResponseEntity<>(bookService.updateBookPrice(id, token, bookPriceDTO), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to update book quantity with its book id
     *
     * @param token           generated token of user
     * @param id              book id to update book quantity of particular book
     * @param bookQuantityDTO object of bookQuantity which sets updated book quantity
     * @return String object of message
     */
    @PutMapping("/updateBookQuantity")
    public ResponseEntity<String> updateBookQuantity(@RequestParam(name = "id") int id,
                                                     @RequestParam(name = "token") String token, @Valid
                                                     @RequestBody BookQuantityDTO bookQuantityDTO) {
        log.info("Inside updateBookQuantity Controller Method");
        return new ResponseEntity<>(bookService.updateBookQuantity(id, token, bookQuantityDTO), HttpStatus.OK);
    }
}
