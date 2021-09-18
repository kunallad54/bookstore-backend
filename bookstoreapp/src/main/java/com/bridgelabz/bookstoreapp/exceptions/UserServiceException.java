package com.bridgelabz.bookstoreapp.exceptions;

public class UserServiceException extends RuntimeException {

    public enum ExceptionType{
        USER_ALREADY_PRESENT,
        EMAIL_NOT_FOUND,
        EMAIL_NOT_VERIFIED
    }

    public UserServiceException.ExceptionType type;

    public UserServiceException(String message, UserServiceException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
