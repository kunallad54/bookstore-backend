package com.bridgelabz.bookstoreapp.builder;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookServiceBuilder {

    /**
     * Purpose : Ability to add book in the database
     *
     * @param bookDTO is object of BookDTO which saves data is repository
     * @return object of Book
     */
    public Book buildDO(BookDTO bookDTO) {
        log.info("Inside buildDO BookServiceBuilder Method");
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        return book;
    }
}
