package com.bridgelabz.bookstoreapp.builder;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.entity.BookModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookServiceBuilder {

    public BookModel buildDO(BookDTO bookDTO){
        log.info("Inside buildDO BookServiceBuilder Method");
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookDTO,bookModel);
        return bookModel;
    }
}
