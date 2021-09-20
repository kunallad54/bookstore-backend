package com.bridgelabz.bookstoreapp.constant;

public enum CommonMessage {

    REGISTRATION_SUCCESSFUL("Registration Successful."),
    EMAIL_VERIFIED("Email Verification Successful."),
    EMAIL_NOT_VERIFIED("Email Verification Failed"),
    LOGIN_SUCCESSFUL("Login Successful."),
    USER_ALREADY_EXIST("User Already Exist."),
    REST_REQUEST_EXCEPTION("Exception in Rest Request."),
    FORGET_PASSWORD("Forget Password."),
    RESET_PASSWORD("Reset Password Successful."),
    BOOK_ADDED_SUCCESSFULLY("Book Added Successfully."),
    GET_BOOKS("List of all books."),
    DELETE_BOOK("Deleted the selected book from the list."),
    BOOK_NOT_FOUND("Book not found."),
    UPDATED_BOOK_PRICE("Updated price of the book."),
    UPDATED_BOOK_QUANTITY("Updated quantity of the book."),
    BOOK_ALREADY_EXIST("Book already exist.");
    private String message;

    CommonMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
