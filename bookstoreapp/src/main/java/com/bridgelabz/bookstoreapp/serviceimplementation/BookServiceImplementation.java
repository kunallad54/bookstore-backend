package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.BookServiceBuilder;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.BookPriceDTO;
import com.bridgelabz.bookstoreapp.dto.BookQuantityDTO;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    /**
     * Purpose : Ability to add new books in book repository which will then save it database
     * if bookDTO data is valid
     *
     * @param token   generated token by user
     * @param bookDTO object of BookDTO taken from user
     * @return String Object of message whether it was able to add a book or not
     */
    @Override
    public String addBook(String token, BookDTO bookDTO) {
        log.info("Inside addBook Book Service Method");
        if (isUserVerified(token)) {
            Optional<Book> bookName = bookRepository.findByName(bookDTO.getName());
            if (bookName.isPresent()) {
                throw new BookStoreException(messageSource.getMessage("book.already.exist",
                        null, Locale.ENGLISH),
                        BookStoreException.ExceptionType.BOOK_ALREADY_EXIST);
            }
            Book book = bookServiceBuilder.buildDO(bookDTO);
            bookRepository.save(book);
            return messageSource.getMessage("book.add.success", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }

    }

    /**
     * Purpose : To get all list of books available in book repository which it will fetch
     * it from database
     *
     * @return List of BookDTO objects from database i.e list of books in DB
     */
    @Override
    public List<BookDTO> getBooks(String token) {
        log.info("Inside getBook Service Method");
        if (isUserVerified(token)) {
            return bookRepository.findAll().stream()
                    .map(book -> modelMapper.map(book, BookDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }

    }

    /**
     * Purpose : Ability to delete book from database with help of its ID
     *
     * @param id book id to delete particular book
     * @return String object of message whether it was deleted successfully or not
     */
    @Override
    public String deleteBook(int id, String token) {
        log.info("Inside deleteBook Book Service Method");
        if (isUserVerified(token)) {
            Book book = findByBookById(id);
            bookRepository.delete(book);
            return messageSource.getMessage("deleted.books", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }


    /**
     * Purpose : Ability to update book price with its updated book price with help of
     * book id
     *
     * @param token        generated token of user
     * @param id           book id to update particular book price
     * @param bookPriceDTO object of BookPriceDTO which sets new updated price of book
     * @return String object of message whether book price was updated or not
     */
    @Override
    public String updateBookPrice(int id, String token, BookPriceDTO bookPriceDTO) {
        log.info("Inside updateBookPrice Service Method");
        if (isUserVerified(token)) {
            Book book = findByBookById(id);
            book.setPrice(bookPriceDTO.getPrice());
            bookRepository.save(book);
            return messageSource.getMessage("updated.price.book", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }

    }

    /**
     * Purpose : Ability to update book quanity with its updated book quantity with help of
     * book id
     *
     * @param token           generated token of user
     * @param id              book id to update particular book quantity
     * @param bookQuantityDTO object of BookQuantityDTO which sets new updated quantiy of book
     * @return String object of message whether book quantity was updated or not
     */
    @Override
    public String updateBookQuantity(int id, String token, BookQuantityDTO bookQuantityDTO) {
        log.info("Inside updateBookQuantity Service Method");
        if (isUserVerified(token)) {
            Book book = findByBookById(id);
            book.setQuantity(bookQuantityDTO.getQuantity());
            bookRepository.save(book);
            return messageSource.getMessage("updated.quantity.book", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : To find book by its id from book repository
     *
     * @param id book id of particular book
     * @return object of book if found or else  throw custom exception
     */
    public Book findByBookById(int id) {
        log.info("Inside findBookById Method");
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(messageSource.getMessage("book.not.found",
                        null, Locale.ENGLISH), BookStoreException.ExceptionType.BOOK_NOT_FOUND));
    }

    /**
     * Purpose : To verify user is valid user or not
     *
     * @param token generated of user
     * @return boolean value true if user verified else false
     */
    private boolean isUserVerified(String token) {
        User userByEmailToken = userServiceImplementation.getUserByEmailToken(token);
        return userByEmailToken.isVerified;
    }

}
