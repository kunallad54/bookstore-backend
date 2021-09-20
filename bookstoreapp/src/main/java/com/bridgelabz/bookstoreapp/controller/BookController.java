package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody @Valid BookDTO bookDTO) {
        log.info("Inside addBook Controller Method");
        return new ResponseEntity<>(bookService.addBook(bookDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getBooks() {
        log.info("Inside getBooks Controller Method");
        List<BookDTO> bookList = bookService.getBooks();
        return new ResponseEntity<>(new ResponseDTO(CommonMessage.GET_BOOKS.getMessage(),
                bookList), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam(name = "id") int id) {
        log.info("Inside deleteBook Controller Method");
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }

    @PutMapping("/updateBookPrice")
    public ResponseEntity<String> updateBookPrice(@RequestParam(name = "id") int id, @Valid
    @RequestBody BookPriceDTO bookPriceDTO) {
        log.info("Inside updateBookPrice Controller Method");
        return new ResponseEntity<>(bookService.updateBookPrice(id, bookPriceDTO), HttpStatus.OK);
    }

    @PutMapping("/updateBookQuantity")
    public ResponseEntity<String> updateBookQuantity(@RequestParam(name = "id") int id, @Valid
    @RequestBody BookQuantityDTO bookQuantityDTO) {
        log.info("Inside updateBookQuantity Controller Method");
        return new ResponseEntity<>(bookService.updateBookQuantity(id,bookQuantityDTO),HttpStatus.OK);
    }
}
