package com.bridgelabz.bookstoreapp.exceptions;

public class BookStoreException extends RuntimeException {

    public enum ExceptionType {
        USER_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        EMAIL_NOT_VERIFIED,
        PASSWORD_INCORRECT,
        UNAUTHORISED_USER,
        BOOK_NOT_FOUND,
        BOOK_ALREADY_EXIST
    }

    public BookStoreException.ExceptionType type;

    public BookStoreException(String message, BookStoreException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
