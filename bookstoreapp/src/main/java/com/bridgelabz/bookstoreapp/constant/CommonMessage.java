package com.bridgelabz.bookstoreapp.constant;

public enum CommonMessage {

    REGISTRATION_SUCCESSFUL("Registration Successful."),
    EMAIL_VERIFIED("Email Verification Successful."),
    LOGIN_SUCCESSFUL("Login Successful."),
    USER_ALREADY_EXIST("User Already Exist."),
    REST_REQUEST_EXCEPTION("Exception in Rest Request."),
    FORGET_PASSWORD("Forget Password."),
    RESET_PASSWORD("Reset Password Successful.");
    private String message;

    CommonMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
