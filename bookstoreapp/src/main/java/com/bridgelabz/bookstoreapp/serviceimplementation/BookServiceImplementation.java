package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.BookServiceBuilder;
import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;
import com.bridgelabz.bookstoreapp.entity.BookModel;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImplementation implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookServiceBuilder bookServiceBuilder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public String addBook(BookDTO bookDTO) {
        log.info("Inside addBook Book Service Method");
        Optional<BookModel> bookName = bookRepository.findByName(bookDTO.getName());
        if(bookName.isPresent()){
            throw new BookStoreException(CommonMessage.BOOK_ALREADY_EXIST.getMessage(),
                    BookStoreException.ExceptionType.BOOK_ALREADY_EXIST);
        }
        BookModel bookModel = bookServiceBuilder.buildDO(bookDTO);
        bookRepository.save(bookModel);
        return CommonMessage.BOOK_ADDED_SUCCESSFULLY.getMessage();
    }

    @Override
    public List<BookDTO> getBooks() {
        log.info("Inside getBook Service Method");
        return bookRepository.findAll().stream()
                .map(bookModel -> modelMapper.map(bookModel,BookDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public String deleteBook(int id) {
        log.info("Inside deleteBook Book Service Method");
        BookModel book = findByBookById(id);
        bookRepository.delete(book);
        return CommonMessage.DELETE_BOOK.getMessage();
    }


    @Override
    public String updateBookPrice(int id, BookPriceDTO bookPriceDTO) {
        log.info("Inside updateBookPrice Service Method");
        BookModel book = findByBookById(id);
        book.setPrice(bookPriceDTO.getPrice());
        bookRepository.save(book);
        return CommonMessage.UPDATED_BOOK_PRICE.getMessage();
    }

    @Override
    public String updateBookQuantity(int id, BookQuantityDTO bookQuantityDTO) {
        log.info("Inside updateBookQuantity Service Method");
        BookModel book = findByBookById(id);
        book.setQuantity(bookQuantityDTO.getQuantity());
        bookRepository.save(book);
        return CommonMessage.UPDATED_BOOK_QUANTITY.getMessage();
    }

    private BookModel findByBookById(int id) {
        log.info("Inside findBookById Method");
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(CommonMessage.BOOK_NOT_FOUND.getMessage(),
                        BookStoreException.ExceptionType.BOOK_NOT_FOUND));
    }
}
